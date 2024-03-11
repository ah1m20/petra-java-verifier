package com.cognitionbox.petra.ast.terms.statements.c;

import com.cognitionbox.petra.ast.terms.Term;



public class C extends Term {
    private final int id;
    public C(int id, boolean valid, int lineError, String errorMessage) {
        super(valid,lineError,errorMessage);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
