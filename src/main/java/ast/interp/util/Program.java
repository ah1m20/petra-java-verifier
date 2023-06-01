package ast.interp.util;

import ast.parsers.ObjParser;
import ast.terms.Obj;
import ast.terms.Prog;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public final class Program {

    public static Prog buildProgram(String rootPath, String entryPointMethodName, String rootObjectName, String... javaSrcFileNames) throws URISyntaxException, IOException {
        List<Obj> objs = new ArrayList<>();
        for (String fileName : javaSrcFileNames){
            File file = new File(rootPath+fileName+".java");
            ObjParser parser = new ObjParser(file.getAbsolutePath(),true);
            Obj o = parser.parse(fileName);
            objs.add(o);
        }
        return new Prog(entryPointMethodName,rootObjectName,objs);
    }

    public static void par(Runnable... runnables){

    }

    public static void mainLoop(long iterationPauseInMilliseconds, Runnable entryPoint, Runnable... runnables){
        while(true){
            for (Runnable r : runnables){
                r.run();
            }
            entryPoint.run();
            if (iterationPauseInMilliseconds>0){
                try {
                    Thread.sleep(iterationPauseInMilliseconds);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
