package com.cognitionbox.petra.ast.parsers;

import com.cognitionbox.petra.ast.terms.*;
import com.cognitionbox.petra.ast.terms.*;
import com.cognitionbox.petra.ast.terms.expressions.e.E;
import com.cognitionbox.petra.ast.terms.statements.c.C;
import com.cognitionbox.petra.ast.interp.util.Collections;
import com.cognitionbox.petra.ast.interp.util.ParserUtils;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public final class ObjParser {
    EParser eparser = new EParser();
    CParser cparser = new CParser();

    private String src;
    public ObjParser(String srcOrFile, boolean isFilePath) throws IOException {
        if (isFilePath){
            BufferedReader reader = new BufferedReader(new FileReader(srcOrFile));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            src = sb.toString();
        } else {
            src = srcOrFile;
        }
    }

    public Obj parse(String className){
        JavaParser javaParser = new JavaParser();
        ParseResult<CompilationUnit> parseResult = javaParser.parse(src);
        if (parseResult.isSuccessful() &&
                parseResult.getResult().isPresent() &&
                        parseResult.getResult().get().getClassByName(className).isPresent()){
            return parse(parseResult.getResult().get().getClassByName(className).get());
        }
        throw new IllegalArgumentException("");
    }

    private Obj parse(ClassOrInterfaceDeclaration declaration){
        if (ParserUtils.isExternalObject(declaration)){
            return ParserUtils.externalObject(declaration);
        } else {
            return parsePetraObject(declaration);
        }
    }

    private Obj parsePetraObject(ClassOrInterfaceDeclaration declaration){
        if (declaration.isInterface() || !declaration.isPublic()){
            return ParserUtils.invalidObj(declaration,"expected public class.",declaration.getNameAsString(), ParserUtils.objectType(declaration));
        }
        Obj obj = new Obj(declaration.getNameAsString(), ParserUtils.objectType(declaration));
        for (FieldDeclaration f : declaration.getFields()){
            if (!f.isPrivate() || !f.isFinal() || f.getVariables().size()!=1){
                return ParserUtils.invalidObj(f,"expected a single private final field.",declaration.getNameAsString(), ParserUtils.objectType(declaration));
            }
            VariableDeclarator v = f.getVariable(0);
            obj.addBeta(new Beta(v.getNameAsString(),v.getTypeAsString()));
        }
        for (MethodDeclaration m : declaration.getMethods()){
            if (!m.isPublic()){
                return ParserUtils.invalidObj(m,"expected public method.",declaration.getNameAsString(), ParserUtils.objectType(declaration));
            }
            if (m.getType().isPrimitiveType() && m.getType().asPrimitiveType().getType().asString().equals("boolean")){
                // process phi
                E e = eparser.parse(m);
                if (ParserUtils.isValid(e)){
                    Phi phi = new Phi(m.isAnnotationPresent(Initial.class),m.getNameAsString(),e);
                    obj.addPhi(phi);
                } else {
                    return ParserUtils.invalidObj(m,"expected valid boolean expression.",declaration.getNameAsString(), ParserUtils.objectType(declaration));
                }
            } else if (m.getType().isVoidType()){
                // process delta
                List<C> cases = cparser.parse(m, ParserUtils.isBaseObject(declaration));
                if (cases.isEmpty()){
                    return ParserUtils.invalidObj(m,"expected body of one if statement or an if-else chain.",declaration.getNameAsString(), ParserUtils.objectType(declaration));
                } else if (!cases.isEmpty() && Collections.forall(cases, c-> ParserUtils.isValid(c))){
                    Delta delta = new Delta(m.getNameAsString(),cases);
                    obj.addDelta(delta);
                } else {
                    return ParserUtils.invalidObj(m,"expected valid method definition.",declaration.getNameAsString(), ParserUtils.objectType(declaration));
                }
            } else {
                return ParserUtils.invalidObj(m,"expected boolean or void method.",declaration.getNameAsString(), ParserUtils.objectType(declaration));
            }
        }
        return obj;
    }


}
