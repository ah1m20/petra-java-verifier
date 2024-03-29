package com.cognitionbox.petra.ast.interp.util;

import com.cognitionbox.petra.ast.parsers.MainParser;
import com.cognitionbox.petra.ast.parsers.ObjParser;
import com.cognitionbox.petra.ast.terms.Obj;
import com.cognitionbox.petra.ast.terms.Prog;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public final class Program {

    public static Prog buildProgram(String rootPath, String entryPointMethodName, String rootObjectName, String... javaSrcFileNames) throws URISyntaxException, IOException {
        List<Obj> objs = new ArrayList<>();
        boolean isReactive = false;
        for (int i=0;i<javaSrcFileNames.length;i++){
            String fileName = javaSrcFileNames[i];
            File file = new File(rootPath+fileName+".java");
            if (i==0){
                MainParser mainParser = new MainParser();
                isReactive = mainParser.isMainReactive(file);
            } else {
                ObjParser parser = new ObjParser(file.getAbsolutePath(),true);
                Obj o = parser.parse(fileName);
                objs.add(o);
            }
        }
        if (isReactive){
            return new Prog(true,entryPointMethodName,rootObjectName,objs);
        } else {
            return new Prog(false,entryPointMethodName,rootObjectName,objs);
        }
    }

    public static void par(Runnable... runnables){
        if (runnables.length==1){
            runnables[0].run();
            return;
        }
        Thread[] threads = new Thread[runnables.length];
        for (int i=0;i<runnables.length;i++){
            threads[i] = new Thread(runnables[i]);
            threads[i].start();
        }
        for (int i=0;i<threads.length;i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void start(Runnable entryPoint){
        entryPoint.run();
    }

    public static void startReactive(long iterationPauseInMilliseconds, Runnable entryPoint, Runnable... runnables){
        while(true){
            entryPoint.run();
            for (Runnable r : runnables){
                r.run();
            }
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
