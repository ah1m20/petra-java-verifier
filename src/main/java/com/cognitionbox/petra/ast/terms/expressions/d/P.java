package com.cognitionbox.petra.ast.terms.expressions.d;

import com.cognitionbox.petra.ast.terms.Terminal;

import java.util.Objects;


public final class P implements D, Terminal {
    private final String p;

    public P(String p) {
        this.p = p;
    }

    public String getP() {
        return p;
    }

    @Override
    public String toString() {
        return p;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof P){
            P p = (P)o;
            if (p.p.equals("true") && !this.p.equals("true")){
                return false;
            } else if (p.p.equals("true") && this.p.equals("true")){
                return true;
            } else if (!p.p.equals("true") && this.p.equals("true")){
                return true;
            } else if (!p.p.equals("true") && !this.p.equals("true")){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (!o.equals("true") && this.p.equals("true")){
//            return false;
//        } else if (o.equals("true") && this.p.equals("true")){
//            return true;
//        } else if (o.equals("true") && !this.p.equals("true")){
//            return true;
//        } else if (!o.equals("true") && !this.p.equals("true")){
//            return true;
//        } else {
//            return false;
//        }
//    }

    @Override
    public int hashCode() {
        return Objects.hash(p);
    }
}
