package ast.terms.expressions.d;

public final class DBinary implements D {
    private final D left;
    private final D right;

    public DBinary(D left, D right) {
        this.left = left;
        this.right = right;
    }

    public D getLeft() {
        return left;
    }
    public D getRight() {
        return right;
    }
}
