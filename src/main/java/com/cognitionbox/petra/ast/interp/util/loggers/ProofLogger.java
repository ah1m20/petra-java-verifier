package com.cognitionbox.petra.ast.interp.util.loggers;

import com.cognitionbox.petra.ast.interp.util.DepthTracker;
import com.cognitionbox.petra.ast.terms.Obj;
import com.cognitionbox.petra.ast.terms.Term;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class ProofLogger {
    private static final boolean proofLoggingEnabled;
    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("petra");
        proofLoggingEnabled = Boolean.parseBoolean(resourceBundle.getString("petra.proofLogging"));
        try {
            try (Stream<Path> walk = Files.walk(Paths.get("./target/petra/proofLogging/"))) {
                walk.sorted(Comparator.reverseOrder())
                        .forEach(p -> {
                            try {
                                Files.delete(p);
                            } catch (IOException e) {}
                        });
            }
        } catch (IOException e) {}
    }
    private final DepthTracker tracker = new DepthTracker();

    public <T> void enter() {
        tracker.markEntry();
    }

    public <T extends Term> void exitWithNonBottom(T t, Obj A, String rule){
        StringBuilder tabs = new StringBuilder();
        //IntStream.range(0,tracker.depth()).forEach(x->tabs.append("|   "));
        logProofLine(A.getFullyQualifiedClassName(),rule,tabs+"["+t+"]"+(A!=null?"^{"+A+"}":"")+" != Bot"+" by "+rule);
        tracker.markExit();
    }

    public <T extends Term> void exitWithBottom(T t, Obj A, String rule){
        StringBuilder tabs = new StringBuilder();
        //IntStream.range(0,tracker.depth()).forEach(x->tabs.append("|   "));
        logProofLine(A.getFullyQualifiedClassName(),rule,tabs+"["+t+"]"+(A!=null?"^{"+A+"}":"")+" = Bot"+" by "+rule);
        tracker.markExit();
    }

    public <T extends Term> boolean exitWithBottom(T value, boolean holds, Obj A, String rule) {
        if (!holds) {
            exitWithBottom(value, A, rule);
            return false;
        }
        return true;
    }

    private void logProofLine(String fullyQualifiedClassName,  String ruleName, String proofLine) {
        String prefix = Thread.currentThread().getStackTrace()[4].toString();
        int diff = 100-prefix.length();
        StringBuilder sb = new StringBuilder();
        IntStream.range(0,diff).forEach(i->sb.append(" "));

        if (proofLoggingEnabled){
            appendProofLineToFile(fullyQualifiedClassName,ruleName,prefix+sb+proofLine);
        } else {
            // discard it
        }
    }

    private void appendProofLineToFile(String fullyQualifiedClassName, String ruleName, String proofLine){
        String rootPath = "./target/petra/proofLogging/";
        String packagePath = fullyQualifiedClassName.substring(0, fullyQualifiedClassName.lastIndexOf('.')).replace('.', File.separatorChar);
        String filePath = rootPath + File.separator + packagePath;
        String className = fullyQualifiedClassName.substring(fullyQualifiedClassName.lastIndexOf('.') + 1);
//        String fileName = className+"_"+ruleName+".ppc";
        String fileName = className+".ppc";
        String fullFilePath = filePath + File.separator + fileName;
        try {
            if (!Files.exists(Paths.get(filePath))){
                Files.createDirectories(Paths.get(filePath));
            }
            Files.write(Paths.get(fullFilePath), Arrays.asList(proofLine),StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
