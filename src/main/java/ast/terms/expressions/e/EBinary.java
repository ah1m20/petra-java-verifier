package ast.terms.expressions.e;



public final class EBinary extends E {
    private final E left;
    private final BinaryOperator operator;
    private final E right;

    public EBinary(E left, BinaryOperator operator, E right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public E getLeft() {
        return left;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public E getRight() {
        return right;
    }

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
//    }
}
