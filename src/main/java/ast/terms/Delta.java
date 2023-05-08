package ast.terms;

import ast.terms.statements.c.C;


import java.util.List;

public final class Delta {

    private final String m;
    private final List<C> overlineC;

    public Delta(String methodLabel, List<C> overlineC) {
        this.m = methodLabel;
        this.overlineC = overlineC;
    }

    public String getM() {
        return m;
    }

    public List<C> getOverlineC() {
        return overlineC;
    }

    
}
