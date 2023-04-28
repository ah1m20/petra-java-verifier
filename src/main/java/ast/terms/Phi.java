package ast.terms;

import ast.terms.expressions.e.E;

public final class Phi {
    private final String p;
    private final E e;

    public Phi(String p, E e) {
        this.p = p;
        this.e = e;
    }

    public String getP() {
        return p;
    }

    public E getE() {
        return e;
    }
}
