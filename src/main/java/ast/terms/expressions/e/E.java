package ast.terms.expressions.e;

import ast.terms.Term;

public class E extends Term {
    public E() {}
    public E(boolean b, int lineNumber, String errorMessage) {
        super(b,lineNumber,errorMessage);
    }
}
