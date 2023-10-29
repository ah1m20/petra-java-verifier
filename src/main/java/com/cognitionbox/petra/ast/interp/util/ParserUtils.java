package com.cognitionbox.petra.ast.interp.util;

import com.cognitionbox.petra.ast.terms.*;
import com.cognitionbox.petra.ast.terms.*;
import com.cognitionbox.petra.ast.terms.expressions.e.E;
import com.cognitionbox.petra.ast.terms.statements.c.C;
import com.cognitionbox.petra.ast.terms.statements.s.Am;
import com.cognitionbox.petra.ast.terms.statements.s.Z;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.util.List;

public final class ParserUtils {

    public static boolean isValid(Object term){
        return term instanceof Term && ((Term)term).isValid();
    }

    public static int getLineNumber(Node node){
        if (node.getRange().isPresent()){
            return node.getRange().get().begin.line;
        } else {
            for (Node n : node.getChildNodes()){
                return getLineNumber(n);
            }
        }
        return -1;
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

    public static Z invalidZ(Node node, String errorMessage){
        return new Z(false,getLineNumber(node),errorMessage);
    }

    public static C invalidC(int id, Node node, String errorMessage){
        return new C(id, false,getLineNumber(node),errorMessage);
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

    public static boolean isExternalObject(ClassOrInterfaceDeclaration declaration){
        return declaration.isAnnotationPresent(External.class);
    }

    public static ObjType objectType(ClassOrInterfaceDeclaration declaration){
        return isBaseObject(declaration)?ObjType.BASE:ObjType.NON_BASE;
    }
}
