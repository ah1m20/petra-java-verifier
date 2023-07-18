package com.cognitionbox.petra.ast.terms.expressions.e;

public final class Not extends EUnary {
    public Not(E inner) {
        super(inner, UnaryOperator.NOT);
    }
}
