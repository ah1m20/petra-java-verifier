package ast.terms.statements.s;

public final class ZBinary implements Z {
    private final Z left;
    private final StatementOperator operator;
    private final Z right;

    public ZBinary(Z left, StatementOperator operator, Z right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Z getLeft() {
        return left;
    }

    public StatementOperator getOperator() {
        return operator;
    }

    public Z getRight() {
        return right;
    }
}
