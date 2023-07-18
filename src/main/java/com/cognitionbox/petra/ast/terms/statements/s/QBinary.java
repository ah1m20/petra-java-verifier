package com.cognitionbox.petra.ast.terms.statements.s;

public final class QBinary extends Q {
    private final Q left;
    private final Q right;

    public QBinary(Q left, Q right) {
        this.left = left;
        this.right = right;
    }

    public Q getLeft() {
        return left;
    }

    public Q getRight() {
        return right;
    }
}
