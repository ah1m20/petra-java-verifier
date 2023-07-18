package com.cognitionbox.petra.ast.parsers;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.File;
import java.io.FileNotFoundException;

public final class MainParser {
    public boolean isMainReactive(File file) throws FileNotFoundException {
        JavaParser javaParser = new JavaParser();
        ParseResult<CompilationUnit> parseResult = javaParser.parse(file);
        if (parseResult.isSuccessful() && parseResult.getResult().isPresent() &&
                parseResult.getResult().get().getClassByName(file.getName().replaceAll("\\.java","")).isPresent()){
            return isMainReactive(parseResult.getResult().get().getClassByName(file.getName().replaceAll("\\.java","")).get());
        }
        throw new IllegalArgumentException();
    }
    private boolean isMainReactive(ClassOrInterfaceDeclaration classOrInterfaceDeclaration){
        for (MethodDeclaration md: classOrInterfaceDeclaration.getMethods()){
            if (md.isStatic() && md.getNameAsString().equals("main")){
                if (md.getBody().get().getStatements().size()==1 &&
                        md.getBody().get().getStatements().get(0).isExpressionStmt() &&
                        md.getBody().get().getStatements().get(0).asExpressionStmt().getExpression().isMethodCallExpr()){
                    if (md.getBody().get().getStatements().get(0).asExpressionStmt().getExpression().asMethodCallExpr().getNameAsString().equals("start")){
                        return false;
                    } else if (md.getBody().get().getStatements().get(0).asExpressionStmt().getExpression().asMethodCallExpr().getNameAsString().equals("startReactive")){
                        return true;
                    }
                }
            } else {
                throw new IllegalArgumentException("Main method not found.");
            }
        }
        throw new IllegalArgumentException("Petra entry point not found within main method.");
    }
}
