package ast.terms.expressions.d;

import ast.terms.Terminal;


public final class P implements D, Terminal {
    private final String p;

    public P(String p) {
        this.p = p;
    }

    public String getP() {
        return p;
    }


    @Override
    public String toString() {
        return p;
    }
}
