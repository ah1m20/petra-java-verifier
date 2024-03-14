package com.cognitionbox.petra.ast.interp;

import com.cognitionbox.petra.ast.interp.util.loggers.ControlledEnglishLogger;
import com.cognitionbox.petra.ast.parsers.MainParser;
import com.cognitionbox.petra.ast.parsers.ObjParser;
import com.cognitionbox.petra.ast.terms.Delta;
import com.cognitionbox.petra.ast.terms.Obj;
import com.cognitionbox.petra.ast.terms.Prog;
import com.cognitionbox.petra.ast.terms.statements.c.C;
import com.cognitionbox.petra.ast.interp.junit.tasks.*;
import com.cognitionbox.petra.ast.terms.statements.c.CBinary;
import com.cognitionbox.petra.ast.terms.statements.c.CUnary;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static com.cognitionbox.petra.ast.interp.PetraControlledEnglish.format;
import static com.cognitionbox.petra.ast.interp.Symbolic.forall;
import static com.cognitionbox.petra.ast.interp.util.Ops.filter;
import static com.cognitionbox.petra.ast.interp.util.Ops.list;
import static org.junit.Assert.assertTrue;

public abstract class Verification {
    private static final ControlledEnglishLogger LOG = new ControlledEnglishLogger();
    private static final boolean controlledEnglishEnabled;
    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("petra");
        controlledEnglishEnabled = Boolean.parseBoolean(resourceBundle.getString("petra.controlledEnglish"));
    }

    private VerificationTask task;
    public Verification(VerificationTask task) {
        this.task = task;
    }

    private static String JAVA_FILE_EXT = ".java";
    private static String ROOT_DIR_PATH = "./src/main/";

    private static final String rootDir = new File(ROOT_DIR_PATH).getPath();

    private static Prog parseSrcFiles(Class<?> root) {
        List<Obj> objs = new ArrayList<>();
        AtomicBoolean isReactive = new AtomicBoolean(false);
        try (Stream<Path> stream = Files.walk(Paths.get(rootDir), Integer.MAX_VALUE)) {
            stream
                    .filter(path -> path.getFileName().toString().contains(JAVA_FILE_EXT))
                    .filter(path -> path.toString().replaceAll("\\\\","\\.").contains(root.getCanonicalName().replaceAll("\\."+root.getSimpleName(),"")))
                    .forEach(path -> {
                        try {
                            File file = path.toFile();
                            if (file.getName().equals("Main.java")){
                                MainParser mainParser = new MainParser();
                                isReactive.set(mainParser.isMainReactive(file));
                            } else {
                                ObjParser parser = new ObjParser(file.getAbsolutePath(),true);
                                Obj obj = parser.parse(file.getName().replaceAll("\\.java",""));
                                if (obj!=null && !obj.isExternal()){
                                    objs.add(obj);
                                }
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Prog(isReactive.get(),root.getSimpleName(),objs);
    }

    private static void addObjRuleTask(Symbolic symbolic, Obj o, List<VerificationTask> tasks){
        tasks.add(new ObjRule(o.getA(), () -> symbolic.interpObj(o).isPresent()));
    }

    private static void addCasesRuleTask(Symbolic symbolic, Obj o, Delta d, List<VerificationTask> tasks){
        tasks.add(new CasesRule(d.getM(), o.getA(), () -> symbolic.interpOverlineC(d.getM(),symbolic.lookupM(d.getM(),o),o).isPresent()));
    }

    private static void addCaseRuleTasks(Symbolic symbolic, int i, String m, C c, Obj o, List<VerificationTask> tasks){
        if (c instanceof CUnary){
            tasks.add(new CaseRule(i, m, o.getA(), () -> symbolic.interpC(m, (CUnary) c, o).isPresent()));
        } else if (c instanceof CBinary){
            tasks.add(new CaseRule(i, m, o.getA(), () -> symbolic.interpC(m, (CUnary) ((CBinary) c).getLeft(), o).isPresent()));
            addCaseRuleTasks(symbolic, i+1, m, ((CBinary) c).getRight(), o, tasks);
        }
    }

    private static void addControlledEnglishTask(Obj o, List<VerificationTask> tasks){
        tasks.add(new ControlledEnglish(o.getA(), () -> {LOG.logControlledEnglishToFile(o.getFullyQualifiedClassName(),format(PetraControlledEnglish.translate(o),14)); return true;} ));
    }

    private static void addEntryPointTask(Symbolic symbolic, Prog prog, List<VerificationTask> tasks){
        tasks.add(new ProveEntryPointTask(prog.getAepsilon(), () -> symbolic.interpProg(prog).isPresent()));
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection verify(Class<?> root) {
        Prog prog = parseSrcFiles(root);
        if (forall(prog.getObjs(), o->o.isValid())){
            List<VerificationTask> tasks = new ArrayList<>();
            Symbolic symbolic = new Symbolic(prog);
            for (Obj o : prog.getObjs()) {
                addObjRuleTask(symbolic,o,tasks);
                for (Delta d : o.getOverlineDelta()) {
                    addCaseRuleTasks(symbolic,0,d.getM(),d.getOverlineC(),o,tasks);
                    addCasesRuleTask(symbolic,o,d,tasks);
                }
                if (controlledEnglishEnabled){
                    addControlledEnglishTask(o,tasks);
                }
            }
            addEntryPointTask(symbolic,prog,tasks);
            return tasks;
        } else {
            return list(filter(prog.getObjs(), o->!o.isValid()), o->new Syntax(o.getA(),o.getLineError(),o.getErrorMessage()));
        }
    }

    @Test
    public void test() {
        assertTrue(task.passed());
    }

}
