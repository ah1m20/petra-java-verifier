package ast.terms.statements.s;

import ast.terms.Term;

public class S extends Term {
    public S(){}
    public S(boolean valid, int lineError, String errorMessage) {
        super(valid, lineError, errorMessage);
    }
}
