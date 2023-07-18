package com.cognitionbox.petra.ast.terms;

import com.cognitionbox.petra.ast.terms.expressions.e.E;


public final class Phi {
    private final boolean initial;
    private final String p;
    private final E e;

    public Phi(boolean initial, String p, E e) {
        this.initial = initial;
        this.p = p;
        this.e = e;
    }

    public boolean isInitial() {
        return initial;
    }
    public String getP() {
        return p;
    }

    public E getE() {
        return e;
    }

    
}
