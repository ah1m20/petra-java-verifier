package ast.terms;

import ast.terms.statements.c.C;


import java.util.List;

public final class Delta {

    private final String m;
    private final List<C> overline_c;

    public Delta(String methodLabel, List<C> overline_c) {
        this.m = methodLabel;
        this.overline_c = overline_c;
    }

    public String getM() {
        return m;
    }

    public List<C> getOverline_c() {
        return overline_c;
    }

    
}
