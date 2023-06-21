package ast.interp;

import ast.interp.junit.tasks.ProveKaseTask;
import ast.interp.junit.tasks.ProveMethodTask;
import ast.interp.junit.tasks.ProveSoundnessAndCompletenessTask;
import ast.interp.junit.tasks.VerificationTask;
import ast.parsers.MainParser;
import ast.parsers.ObjParser;
import ast.terms.Delta;
import ast.terms.Obj;
import ast.terms.Prog;
import ast.terms.statements.c.C;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class Verification {

    private static List<VerificationTask> tasks = new ArrayList<>();

    private VerificationTask task;
    public Verification(VerificationTask task) {
        this.task = task;
    }

    private static String JAVA_FILE_EXT = ".java";
    private static String ROOT_DIR_PATH = "./";

    private static final String rootDir = new File(ROOT_DIR_PATH).getPath();

    private static Prog parseSrcFiles(Class<?> root) {
        List<Obj> objs = new ArrayList<>();
        AtomicBoolean isReactive = new AtomicBoolean(false);
        try (Stream<Path> stream = Files.walk(Paths.get(rootDir), Integer.MAX_VALUE)) {
            stream
                    .filter(path -> path.getFileName().toString().contains(JAVA_FILE_EXT))
                    .filter(path -> path.toString().contains(root.getCanonicalName().replaceAll("\\."+root.getSimpleName(),"")))
                    .forEach(path -> {
                        try {
                            File file = path.toFile();
                            if (file.getName().equals("Main.java")){
                                MainParser mainParser = new MainParser();
                                isReactive.set(mainParser.isMainReactive(file));
                            } else {
                                ObjParser parser = new ObjParser(file.getAbsolutePath(),true);
                                Optional<Obj> optional = parser.parse(file.getName().replaceAll("\\.java",""));
                                optional.ifPresent(o->objs.add(o));
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
        Symbolic symbolic = new Symbolic(prog);
        for (Obj o : prog.getObjs()) {
            if (o instanceof Obj) {
                tasks.add(new ProveSoundnessAndCompletenessTask(o.getA(), () -> symbolic.interpObj(o).isPresent()));
                for (Delta d : o.getOverlineDelta()) {
                    for (int i = 0; i < d.getOverlineC().size(); i++) {
                        C c = d.getOverlineC().get(i);
                        tasks.add(new ProveKaseTask(i, d.getM(), o.getA(), () -> symbolic.interpC(c, o).isPresent()));
                    }
                    tasks.add(new ProveMethodTask(d.getM(), o.getA(), () -> symbolic.pairwiseDisjointDomC(d.getOverlineC(), o)));
                }
            }
        }
        return tasks;
    }

    @Test
    public void test() {
        assertTrue(task.passed());
    }

}
