package com.cognitionbox.petra.ast.interp.util.loggers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public final class ControlledEnglishLogger {
    static {
        try {
            try (Stream<Path> walk = Files.walk(Paths.get("./target/petra/controlledEnglish/"))) {
                walk.sorted(Comparator.reverseOrder())
                        .forEach(p -> {
                            try {
                                Files.delete(p);
                            } catch (IOException e) {}
                        });
            }
        } catch (IOException e) {}
    }
    public void logControlledEnglishToFile(String fullyQualifiedClassName, String controlledenglish){
        String rootPath = "./target/petra/controlledEnglish/";
        String packagePath = fullyQualifiedClassName.substring(0, fullyQualifiedClassName.lastIndexOf('.')).replace('.', File.separatorChar);
        String filePath = rootPath + File.separator + packagePath;
        String className = fullyQualifiedClassName.substring(fullyQualifiedClassName.lastIndexOf('.') + 1);
        String fileName = className+".pce";
        String fullFilePath = filePath + File.separator + fileName;
        try {
            if (!Files.exists(Paths.get(filePath))){
                Files.createDirectories(Paths.get(filePath));
            }
            Files.write(Paths.get(fullFilePath), Arrays.asList(controlledenglish));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
