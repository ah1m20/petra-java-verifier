package com.cognitionbox.petra.ast.terms.statements.s;


public class G extends S {
    private final String g;

    public G(String g) {
        this.g = g;
    }

    public G(int lineError, String errorMessage) {
        super(true,lineError,errorMessage);
        this.g = null;
    }

    public String getG() {
        return g;
    }


    @Override
    public String toString() {
        return g ;
    }
}
