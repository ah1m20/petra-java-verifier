package com.cognitionbox.petra.ast.terms.statements;

import com.cognitionbox.petra.ast.terms.Terminal;
import com.cognitionbox.petra.ast.terms.expressions.d.D;

import java.util.Objects;


public final class A implements Terminal {
    private final String A;

    public A(String A) {
        this.A = A;
    }

    public String getA() {
        return A;
    }

    @Override
    public String toString() {
        return A;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        A m1 = (A) o;
        return Objects.equals(A, m1.A);
    }

    @Override
    public int hashCode() {
        return Objects.hash(A);
    }
}
