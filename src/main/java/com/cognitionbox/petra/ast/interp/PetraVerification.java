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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.cognitionbox.petra.ast.interp.Symbolic.forall;
import static com.cognitionbox.petra.ast.interp.util.Ops.filter;
import static com.cognitionbox.petra.ast.interp.util.Ops.list;
import static org.junit.Assert.assertTrue;

public abstract class PetraVerification {
    private static final ControlledEnglishLogger LOG = new ControlledEnglishLogger();
    private static final boolean controlledEnglishEnabled;
    private static final boolean dotDiagramsEnabled;
    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("petra");
        controlledEnglishEnabled = Boolean.parseBoolean(resourceBundle.getString("petra.controlledEnglish"));
        dotDiagramsEnabled = Boolean.parseBoolean(resourceBundle.getString("petra.dotDiagrams"));
    }

    private PetraTask task;
    public PetraVerification(PetraTask task) {
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

    private static void addObjRuleTask(int sequenceNo, Symbolic symbolic, Obj o, List<PetraTask> tasks){
        tasks.add(new ObjRule(sequenceNo,symbolic,o));
    }

    private static void addCasesRuleTask(int sequenceNo, Symbolic symbolic, Obj o, Delta d, List<PetraTask> tasks){
        tasks.add(new CasesRule(sequenceNo,symbolic,o,d));
    }

    private static void addCaseRuleTasks(int sequenceNo, Symbolic symbolic, Obj o, Delta d, C c, List<PetraTask> tasks){
        if (c instanceof CUnary){
            tasks.add(new CaseRule(sequenceNo, symbolic, o, d, (CUnary) c));
        } else if (c instanceof CBinary){
            tasks.add(new CaseRule(sequenceNo, symbolic, o, d, (CUnary) ((CBinary) c).getLeft()));
            addCaseRuleTasks(sequenceNo, symbolic, o, d, ((CBinary) c).getRight(), tasks);
        }
    }

    private static void addControlledEnglishTask(Obj o, List<PetraTask> tasks){
        tasks.add(new ControlledEnglish(LOG,o));
    }

    private static void addDotDiagramTask(Symbolic symbolic, Obj o, List<PetraTask> tasks){
        tasks.add(new DotDiagramTask(symbolic,o));
    }

    private static void addEntryPointTask(int sequenceNo, Symbolic symbolic, Prog prog, List<PetraTask> tasks){
        tasks.add(new ProveEntryPointTask(sequenceNo,symbolic,prog));
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection verify(Class<?> root) {
        Prog prog = parseSrcFiles(root);
        if (forall(prog.getObjs(), o->o.isValid())){
            List<PetraTask> tasks = new ArrayList<>();
            Symbolic symbolic = new Symbolic(prog);
            AtomicInteger sequencer = new AtomicInteger();
            for (Obj o : prog.getObjs()) {
                addObjRuleTask(sequencer.getAndIncrement(),symbolic,o,tasks);
                for (Delta d : o.getOverlineDelta()) {
                    addCaseRuleTasks(sequencer.getAndIncrement(),symbolic,o,d,d.getOverlineC(),tasks);
                    addCasesRuleTask(sequencer.getAndIncrement(),symbolic,o,d,tasks);
                }
                if (controlledEnglishEnabled){
                    addControlledEnglishTask(o,tasks);
                }
                if (dotDiagramsEnabled){
                    addDotDiagramTask(symbolic,o,tasks);
                }
            }
            if (prog.getM()!=null){
                addEntryPointTask(sequencer.getAndIncrement(),symbolic,prog,tasks);
            }
            return tasks;
        } else {
            return list(filter(prog.getObjs(), o->!o.isValid()), o->new SyntaxTask(o.getA(),o.getLineError(),o.getErrorMessage()));
        }
    }

    @Test
    public void test() throws Exception {
        assertTrue(task.call());
    }

}
