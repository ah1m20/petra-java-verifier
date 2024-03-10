package com.cognitionbox.petra.ast.terms;

import com.cognitionbox.petra.ast.terms.statements.c.C;


import java.util.List;

public final class Delta extends Term {

    private final String m;
    private final C overlineC;

    public Delta(String methodLabel, C overlineC) {
        this.m = methodLabel;
        this.overlineC = overlineC;
    }

    public Delta(boolean valid, int lineError, String errorMessage, String methodLabel, C overlineC) {
        super(valid, lineError, errorMessage);
        this.m = methodLabel;
        this.overlineC = overlineC;
    }

    public String getM() {
        return m;
    }

    public C getOverlineC() {
        return overlineC;
    }

    @Override
    public String toString(){
//        StringBuilder sb = new StringBuilder();
//        sb.append("c0");
//        for (int i=1;i<overlineC.size();i++){
//            sb.append("+c"+i);
//        }
        return overlineC.toString();
    }
}
