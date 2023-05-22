package ast.parsers;

import ast.terms.*;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        throw new IllegalArgumentException();
    }

    private Obj parse(ClassOrInterfaceDeclaration declaration){
        if (declaration.isInterface() || !declaration.isPublic()){
            throw new IllegalArgumentException("expected public class.");
        }
        if (isPrimitiveObject(declaration)){
            return parsePrimitive(declaration);
        } else {
            return parseNonPrimitive(declaration);
        }
    }

    private boolean isPrimitiveObject(ClassOrInterfaceDeclaration declaration){
        return declaration.isAnnotationPresent(Base.class);
    }

    private Obj parseNonPrimitive(ClassOrInterfaceDeclaration declaration){
        Obj obj = new Obj(declaration.getNameAsString(),false);
        for (FieldDeclaration f : declaration.getFields()){
            if (!f.isPrivate() || !f.isFinal() || f.getVariables().size()!=1){
                throw new IllegalArgumentException("expected a single private final field.");
            }
            VariableDeclarator v = f.getVariable(0);
            obj.addBeta(new Beta(v.getNameAsString(),v.getTypeAsString()));
        }
        for (MethodDeclaration m : declaration.getMethods()){
            if (!m.isPublic()){
                throw new IllegalArgumentException("expected public method.");
            }
            if (m.getType().isPrimitiveType() && m.getType().asPrimitiveType().getType().asString().equals("boolean")){
                // process phi
                Phi phi = new Phi(m.getNameAsString(),eparser.parse(m));
                obj.addPhi(phi);
            } else if (m.getType().isVoidType()){
                // process delta
                Delta delta = new Delta(m.getNameAsString(),cparser.parse(m,false));
                obj.addDelta(delta);
            } else {
                throw new IllegalArgumentException("expected boolean or void method.");
            }
        }
        return obj;
    }

    private Obj parsePrimitive(ClassOrInterfaceDeclaration declaration){
        Obj obj = new Obj(declaration.getNameAsString(),true);
        for (MethodDeclaration m : declaration.getMethods()){
            if (!m.isPublic()){
                throw new IllegalArgumentException("expected public method.");
            }
            if (m.getType().isPrimitiveType() && m.getType().asPrimitiveType().getType().asString().equals("boolean")){
                // process phi
                Phi phi = new Phi(m.getNameAsString(),null);
                obj.addPhi(phi);
            } else if (m.getType().isVoidType()){
                // process delta
                Delta delta = new Delta(m.getNameAsString(),cparser.parse(m,true));
                obj.addDelta(delta);
            } else {
                throw new IllegalArgumentException("expected boolean or void method.");
            }
        }
        return obj;
    }
}
