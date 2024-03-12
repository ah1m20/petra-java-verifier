package com.cognitionbox.petra.ast.interp;

import com.cognitionbox.petra.ast.terms.Beta;
import com.cognitionbox.petra.ast.terms.Delta;
import com.cognitionbox.petra.ast.terms.Obj;
import com.cognitionbox.petra.ast.terms.Phi;
import com.cognitionbox.petra.ast.terms.expressions.PrePost;
import com.cognitionbox.petra.ast.terms.expressions.d.D;
import com.cognitionbox.petra.ast.terms.expressions.d.DBinary;
import com.cognitionbox.petra.ast.terms.expressions.d.P;
import com.cognitionbox.petra.ast.terms.expressions.e.*;
import com.cognitionbox.petra.ast.terms.statements.c.C;
import com.cognitionbox.petra.ast.terms.statements.c.CUnary;
import com.cognitionbox.petra.ast.terms.statements.c.CBinary;
import com.cognitionbox.petra.ast.terms.statements.s.*;
import com.google.common.base.CaseFormat;

public final class PetraControlledEnglish {
    public static String translate(Obj obj){

        String A = convert(obj.getA());

        StringBuilder betas = new StringBuilder();
        for (Beta beta : obj.getOverlineBeta()){
            betas.append("a component "+convert(beta.getFieldId())+" of type "+convert(beta.getObjectId())+", ");
        }

        StringBuilder states = new StringBuilder();
        StringBuilder stateMeanings = new StringBuilder();
        for (Phi phi : obj.getOverlinePhi()){
            states.append(convert(phi.getP())+", ");

            if (obj.isPrimitive()){
                stateMeanings.append(convert(phi.getP())+" has an assumed meaning, ");
            } else {
                stateMeanings.append(convert(phi.getP())+" means "+translate(phi.getE())+", ");
            }
        }

        StringBuilder deltas = new StringBuilder();
        for (Delta delta : obj.getOverlineDelta()){
            StringBuilder cases = new StringBuilder();
            cases.append(translate(delta.getOverlineC()));
            deltas.append("an action "+convert(delta.getM())+" where "+cases+", ");
        }

        return A+" is a system which has "+betas+"and is in a state which is one of "+states+" where "+stateMeanings+"and has "+deltas+"and nothing else.";
    }

    private static String translate(E e){
        if (e instanceof True){
            return "any possible state";
        } else if (e instanceof Ap){
            return convert(((Ap) e).getA())+" "+convert(((Ap) e).getP());
        } else if (e instanceof EUnary && ((EUnary) e).getOperator()== UnaryOperator.PAREN ){
            return "("+translate(((EUnary) e).getInner())+")";
        } else if (e instanceof EUnary && ((EUnary) e).getOperator()==UnaryOperator.NOT ){
            return "not "+translate(((EUnary) e).getInner());
        } else if (e instanceof EBinary && ((EBinary) e).getOperator()==BinaryOperator.AND ){
            return translate(((EBinary) e).getLeft())+" and "+translate(((EBinary) e).getRight());
        } else if (e instanceof EBinary && ((EBinary) e).getOperator()==BinaryOperator.OR ){
            return translate(((EBinary) e).getLeft())+" or "+translate(((EBinary) e).getRight());
        } else if (e instanceof EBinary && ((EBinary) e).getOperator()==BinaryOperator.XOR ){
            return translate(((EBinary) e).getLeft())+" either "+translate(((EBinary) e).getRight());
        }
        throw new IllegalArgumentException();
    }

    private static String translate(PrePost prePost){
        return translate((D)prePost);
    }

    private static String translate(D d){
        if (d instanceof P){
            return convert(((P) d).getP());
        } else if (d instanceof DBinary){
            return translate(((DBinary) d).getLeft())+" or "+translate(((DBinary) d).getRight());
        }
        throw new IllegalArgumentException();
    }

    private static String translate(S s){
        if (s==null || s instanceof G){
            return "assume";
        } else if (s instanceof Skip){
            return "do nothing";
        } else if (s instanceof Z){
            return translate((Z)s);
        }
        throw new IllegalArgumentException();
    }

    private static String translate(Z z){
        if (z instanceof Q){
            return translate((Q)z);
        } else if (z instanceof ZBinary){
            return translate(((ZBinary) z).getLeft())+" separately with "+translate(((ZBinary) z).getRight());
        }
        throw new IllegalArgumentException();
    }

    private static String translate(Q q){
        if (q instanceof Am){
            return convert(((Am) q).getM())+" "+convert(((Am) q).getA());
        } else if (q instanceof QBinary){
            return translate(((QBinary) q).getLeft())+" before "+translate(((QBinary) q).getRight());
        }
        throw new IllegalArgumentException();
    }

    private static String translate(C c){
        if (c instanceof CUnary){
            return translate((CUnary)c);
        } else if (c instanceof CBinary){
            return translate(((CBinary)c).getLeft())+", or "+translate(((CBinary) c).getRight());
        }
        throw new IllegalArgumentException();
    }
    private static String translate(CUnary c){
        return "given "+translate(c.getPre())+", when "+translate(c.getS())+" completes, then "+translate(c.getPost());
    }

    public static String format(String s, int wordsPerLine){
        String[] split = s.split(" ");
        StringBuilder output = new StringBuilder();
        for (int i=0;i<split.length;i++){
            output.append(split[i]+" ");
            if (i!=0 && i%wordsPerLine==0){
                output.append("\n");
            }
        }
        return output.toString();
    }

    private static String convert(String s){
        if (Character.isUpperCase(s.charAt(0))){
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,s).replaceAll("_"," ");
        } else {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,s).replaceAll("_"," ");
        }
    }
}
