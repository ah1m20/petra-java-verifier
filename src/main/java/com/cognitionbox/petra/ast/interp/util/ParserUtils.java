package com.cognitionbox.petra.ast.interp.util;

import com.cognitionbox.petra.ast.terms.*;
import com.cognitionbox.petra.ast.terms.expressions.e.E;
import com.cognitionbox.petra.ast.terms.statements.c.C;
import com.cognitionbox.petra.ast.terms.statements.c.CUnary;
import com.cognitionbox.petra.ast.terms.statements.s.Am;
import com.cognitionbox.petra.ast.terms.statements.s.Z;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

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

    public static Obj invalidObj(boolean entry, ClassOrInterfaceDeclaration declaration, Node node, String errorMessage, String objectName, ObjType objType){
        return new Obj(entry,declaration.getFullyQualifiedName().get(),false,getLineNumber(node),errorMessage,objectName,objType);
    }

    public static Obj invalidObj(boolean entry, ClassOrInterfaceDeclaration declaration, String errorMessage, String objectName, ObjType objType){
        return invalidObj(entry,declaration,declaration,errorMessage,objectName,objType);
    }

    public static E invalidE(Node node, String errorMessage){
        return new E(false,getLineNumber(node),errorMessage);
    }

    public static Delta invalidDelta(boolean entry, Node node, String errorMessage, String methodLabel, C overlineC){
        return new Delta(entry,false,getLineNumber(node),errorMessage,methodLabel,overlineC);
    }

    public static Z invalidZ(Node node, String errorMessage){
        return new Z(false,getLineNumber(node),errorMessage);
    }

    public static C invalidC(int id, Node node, String errorMessage){
        return new CUnary(id, false,getLineNumber(node),errorMessage);
    }

    public static Am invalidAm(Node node, String errorMessage){
        return new Am(false,getLineNumber(node),errorMessage);
    }

    public static Obj externalObject(ClassOrInterfaceDeclaration declaration){
        return new Obj(false,declaration.getFullyQualifiedName().get(),declaration.getNameAsString(),ObjType.EXTERNAL);
    }

    public static boolean isEntry(MethodDeclaration declaration){
        return declaration.isAnnotationPresent(Entry.class);
    }
    public static boolean isEntry(ClassOrInterfaceDeclaration declaration){
        return declaration.isAnnotationPresent(Entry.class);
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
