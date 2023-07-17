package ast.interp;

import ast.terms.Beta;
import ast.terms.Delta;
import ast.terms.Obj;
import ast.terms.Phi;
import ast.terms.expressions.PrePost;
import ast.terms.expressions.d.D;
import ast.terms.expressions.d.DBinary;
import ast.terms.expressions.d.P;
import ast.terms.expressions.e.*;
import ast.terms.statements.c.C;
import ast.terms.statements.s.*;

public final class PetraControlledEnglish {
    public static String translate(Obj obj){

        String A = obj.getA();

        StringBuilder betas = new StringBuilder();
        for (Beta beta : obj.getOverlineBeta()){
            betas.append("a component "+beta.getFieldId()+" of type "+beta.getObjectId()+", ");
        }

        StringBuilder states = new StringBuilder();
        StringBuilder stateMeanings = new StringBuilder();
        for (Phi phi : obj.getOverlinePhi()){
            states.append(phi.getP()+",");
            stateMeanings.append(phi.getP()+" means "+translate(phi.getE())+", ");
        }

        StringBuilder deltas = new StringBuilder();
        for (Delta delta : obj.getOverlineDelta()){
            StringBuilder cases = new StringBuilder();
            cases.append(translate(delta.getOverlineC().get(0)));
            for (int i=1; i<delta.getOverlineC().size(); i++){
                cases.append(", or "+translate(delta.getOverlineC().get(i)));
            }
            deltas.append("an action "+delta.getM()+" where "+cases+", ");
        }

        return A+" is a system which has "+betas+"and is in a state which is one of "+states+" where "+stateMeanings+"and has "+deltas+"and nothing else.";
    }

    private static String translate(E e){
        if (e instanceof True){
            return "any possible state";
        } else if (e instanceof Ap){
            return ((Ap) e).getA()+" "+((Ap) e).getP();
        } else if (e instanceof EUnary && ((EUnary) e).getOperator()==UnaryOperator.PAREN ){
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
            return ((P) d).getP();
        } else if (d instanceof DBinary){
            return translate(((DBinary) d).getLeft())+", or "+translate(((DBinary) d).getRight());
        }
        throw new IllegalArgumentException();
    }

    private static String translate(S s){
        if (s==null){
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
            return ((Am) q).getM()+" "+((Am) q).getA();
        } else if (q instanceof QBinary){
            return translate(((QBinary) q).getLeft())+" before "+translate(((QBinary) q).getRight());
        }
        throw new IllegalArgumentException();
    }

    private static String translate(C c){
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
}
