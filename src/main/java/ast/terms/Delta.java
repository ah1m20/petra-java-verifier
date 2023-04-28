package ast.terms;

import ast.terms.statements.c.C;

import java.util.ArrayList;
import java.util.List;

public final class Delta {
    private final String m;
    private final List<C> overline_c = new ArrayList<C>();

    public Delta(String methodLabel) {
        this.m = methodLabel;
    }

    public String getM() {
        return m;
    }

    public void addCase(C c) {
        this.overline_c.add(c);
    }
}
