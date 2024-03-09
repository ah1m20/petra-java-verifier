package com.cognitionbox.petra.ast.interp;

import com.cognitionbox.petra.ast.interp.util.loggers.Logger;
import com.cognitionbox.petra.ast.parsers.MainParser;
import com.cognitionbox.petra.ast.parsers.ObjParser;
import com.cognitionbox.petra.ast.terms.Delta;
import com.cognitionbox.petra.ast.terms.Obj;
import com.cognitionbox.petra.ast.terms.Prog;
import com.cognitionbox.petra.ast.terms.statements.c.C;
import com.cognitionbox.petra.ast.interp.junit.tasks.*;
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

    private static final Logger LOG = new Logger();

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
        if (isReactive.get()){
            return new Prog(true,"run",root.getSimpleName(),objs);
        } else {
            return new Prog(false,"run",root.getSimpleName(),objs);
        }
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection verify(Class<?> root) {
        Prog prog = parseSrcFiles(root);
        if (forall(prog.getObjs(), o->o.isValid())){
            List<VerificationTask> tasks = new ArrayList<>();
            //tasks.add(new ProveEntryPointTask(prog.getAepsilon(), () -> new Symbolic(prog).interpProgQuick(prog).isPresent()));
            for (Obj o : prog.getObjs()) {
                if (o instanceof Obj) {
                    tasks.add(new ControlledEnglishTask(o.getA(), () -> {LOG.info(format(PetraControlledEnglish.translate(o),14)); return true;} ));
                    tasks.add(new ProveSoundnessAndCompletenessTask(o.getA(), () -> new Symbolic(prog).interpObj(o).isPresent()));
                    for (Delta d : o.getOverlineDelta()) {
                        for (int i = 0; i < d.getOverlineC().size(); i++) {
                            C c = d.getOverlineC().get(i);
                            tasks.add(new ProveKaseTask(i, d.getM(), o.getA(), () -> new Symbolic(prog).interpC(d.getM(),c, o).isPresent()));
                        }
                        tasks.add(new ProveMethodTask(d.getM(), o.getA(), () -> {
                            Symbolic symbolic = new Symbolic(prog);
                            return symbolic.interpOverlineC(d.getM(),symbolic.lookupM(d.getM(),o),o).isPresent();
                        }));
                    }
                }
            }
            return tasks;
        } else {
            return list(filter(prog.getObjs(), o->!o.isValid()), o->new SyntaxInvalid(o.getA(),o.getLineError(),o.getErrorMessage()));
        }
    }

    @Test
    public void test() {
        assertTrue(task.passed());
    }

}
