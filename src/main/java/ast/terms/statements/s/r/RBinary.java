package ast.terms.statements.s.r;

import ast.terms.statements.s.S;

public final class RBinary implements S, R {
    private final R left;
    private final R right;

    public RBinary(R left, R right) {
        this.left = left;
        this.right = right;
    }

    public R getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public String toString() {
        return left +
                " // " + right;
    }
}
