package ast.terms.statements.s;

public final class SBinary implements S, Binary {
    private final S left;
    private final S right;

    public SBinary(S left, S right) {
        this.left = left;
        this.right = right;
    }

    public S getLeft() {
        return left;
    }

    public S getRight() {
        return right;
    }
}
