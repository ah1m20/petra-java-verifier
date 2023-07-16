package ast.terms.statements.s;

public final class ZBinary extends Z {
    private final Z left;
    private final Z right;

    public ZBinary(Z left, Z right) {
        this.left = left;
        this.right = right;
    }

    public Z getLeft() {
        return left;
    }

    public Z getRight() {
        return right;
    }
}
