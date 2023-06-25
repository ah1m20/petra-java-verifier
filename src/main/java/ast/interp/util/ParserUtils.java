package ast.interp.util;

import ast.terms.*;
import ast.terms.expressions.e.E;
import ast.terms.statements.c.C;
import ast.terms.statements.s.Am;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.util.List;

public final class ParserUtils {

    public static boolean isValid(Object term){
        return term instanceof Term && ((Term)term).isValid();
    }

    public static int getLineNumber(Node node){
        return node.getRange().get().begin.line;
    }
    public static Obj invalidObj(Node node, String errorMessage, String objectName, ObjType objType){
        return new Obj(false,getLineNumber(node),errorMessage,objectName,objType);
    }

    public static E invalidE(Node node, String errorMessage){
        return new E(false,getLineNumber(node),errorMessage);
    }

    public static Delta invalidDelta(Node node, String errorMessage, String methodLabel, List<C> overlineC){
        return new Delta(false,getLineNumber(node),errorMessage,methodLabel,overlineC);
    }

    public static C invalidC(Node node, String errorMessage){
        return new C(false,getLineNumber(node),errorMessage);
    }

    public static Am invalidAm(Node node, String errorMessage){
        return new Am(false,getLineNumber(node),errorMessage);
    }

    public static Obj externalObject(ClassOrInterfaceDeclaration declaration){
        return new Obj(declaration.getNameAsString(),ObjType.EXTERNAL);
    }

    public static boolean isBaseObject(ClassOrInterfaceDeclaration declaration){
        return declaration.isAnnotationPresent(Base.class);
    }

    public static ObjType objectType(ClassOrInterfaceDeclaration declaration){
        return isBaseObject(declaration)?ObjType.BASE:ObjType.NON_BASE;
    }
}
