package ast.terms.expressions.e;

import ast.terms.Terminal;


public final class Ap implements E, Terminal {
    private final String a;
    private final String p;

    public Ap(String a, String p) {
        this.a = a;
        this.p = p;
    }

    public String getA() {
        return a;
    }

    public String getP() {
        return p;
    }

    
}
