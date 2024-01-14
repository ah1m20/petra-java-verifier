package com.cognitionbox.petra.ast.terms.statements.s;


public class Am extends Q {
    private final String a;
    private final String m;

    public Am(String a, String m) {
        this.a = a;
        this.m = m;
    }

    public Am(boolean valid, int lineError, String errorMessage) {
        super(valid,lineError,errorMessage);
        this.a = null;
        this.m = null;
    }

    public String getA() {
        return a;
    }

    public String getM() {
        return m;
    }

    @Override
    public String toString() {
        return a + "." + m;
    }
}
