package com.cognitionbox.petra.ast.terms;

import com.cognitionbox.petra.ast.terms.statements.c.C;


import java.util.List;

public final class Delta extends Term {

    private final String m;
    private final List<C> overlineC;

    public Delta(String methodLabel, List<C> overlineC) {
        this.m = methodLabel;
        this.overlineC = overlineC;
    }

    public Delta(boolean valid, int lineError, String errorMessage, String methodLabel, List<C> overlineC) {
        super(valid, lineError, errorMessage);
        this.m = methodLabel;
        this.overlineC = overlineC;
    }

    public String getM() {
        return m;
    }

    public List<C> getOverlineC() {
        return overlineC;
    }

    @Override
    public String toString(){
        return m;
    }
}
