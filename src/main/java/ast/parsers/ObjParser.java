package ast.parsers;

import ast.terms.*;
import ast.terms.expressions.e.E;
import ast.terms.statements.c.C;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static ast.interp.util.Collections.*;
import static ast.interp.util.ParserUtils.*;

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
        if (isExternalObject(declaration)){
            return externalObject(declaration);
        } else {
            return parsePetraObject(declaration);
        }
    }

    private Obj parsePetraObject(ClassOrInterfaceDeclaration declaration){
        if (declaration.isInterface() || !declaration.isPublic()){
            return invalidObj(declaration,"expected public class.",declaration.getNameAsString(), objectType(declaration));
        }
        Obj obj = new Obj(declaration.getNameAsString(), objectType(declaration));
        for (FieldDeclaration f : declaration.getFields()){
            if (!f.isPrivate() || !f.isFinal() || f.getVariables().size()!=1){
                return invalidObj(f,"expected a single private final field.",declaration.getNameAsString(), objectType(declaration));
            }
            VariableDeclarator v = f.getVariable(0);
            obj.addBeta(new Beta(v.getNameAsString(),v.getTypeAsString()));
        }
        for (MethodDeclaration m : declaration.getMethods()){
            if (!m.isPublic()){
                return invalidObj(m,"expected public method.",declaration.getNameAsString(), objectType(declaration));
            }
            if (m.getType().isPrimitiveType() && m.getType().asPrimitiveType().getType().asString().equals("boolean")){
                // process phi
                E e = eparser.parse(m);
                if (isValid(e)){
                    Phi phi = new Phi(m.isAnnotationPresent(Initial.class),m.getNameAsString(),e);
                    obj.addPhi(phi);
                } else {
                    return invalidObj(m,"expected valid boolean expression.",declaration.getNameAsString(), objectType(declaration));
                }
            } else if (m.getType().isVoidType()){
                // process delta
                List<C> cases = cparser.parse(m, isBaseObject(declaration));
                if (cases.isEmpty()){
                    return invalidObj(m,"expected body of one if statement or an if-else chain.",declaration.getNameAsString(), objectType(declaration));
                } else if (!cases.isEmpty() && forall(cases, c->isValid(c))){
                    Delta delta = new Delta(m.getNameAsString(),cases);
                    obj.addDelta(delta);
                } else {
                    return invalidObj(m,"expected valid method definition.",declaration.getNameAsString(), objectType(declaration));
                }
            } else {
                return invalidObj(m,"expected boolean or void method.",declaration.getNameAsString(), objectType(declaration));
            }
        }
        return obj;
    }


}
