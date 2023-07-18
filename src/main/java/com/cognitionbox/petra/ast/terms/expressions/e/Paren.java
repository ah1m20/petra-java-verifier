package com.cognitionbox.petra.ast.terms.expressions.e;

public final class Paren extends EUnary {
    public Paren(E inner) {
        super(inner, UnaryOperator.PAREN);
    }
}
