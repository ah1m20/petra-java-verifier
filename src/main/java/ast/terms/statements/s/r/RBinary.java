package ast.terms.statements.s.r;

import ast.terms.statements.s.Binary;
import ast.terms.statements.s.S;

public final class RBinary implements S, R, Binary {
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
}
