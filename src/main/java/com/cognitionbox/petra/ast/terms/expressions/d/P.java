package com.cognitionbox.petra.ast.terms.expressions.d;

import com.cognitionbox.petra.ast.terms.Terminal;


public final class P implements D, Terminal {
    private final String p;

    public P(String p) {
        this.p = p;
    }

    public String getP() {
        return p;
    }

    
}
