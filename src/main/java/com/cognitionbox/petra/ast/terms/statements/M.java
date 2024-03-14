package com.cognitionbox.petra.ast.terms.statements;

import com.cognitionbox.petra.ast.terms.Terminal;
import com.cognitionbox.petra.ast.terms.expressions.d.D;

import java.util.Objects;


public final class M implements D, Terminal {
    private final String m;

    public M(String m) {
        this.m = m;
    }

    public String getM() {
        return m;
    }

    @Override
    public String toString() {
        return m;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        M m1 = (M) o;
        return Objects.equals(m, m1.m);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m);
    }
}
