package ast.terms.expressions.e;



public class EUnary implements E{
    private final E inner;
    private final UnaryOperator operator;
    public EUnary(E inner, UnaryOperator operator) {
        this.inner = inner;
        this.operator = operator;
    }

    public E getInner() {
        return inner;
    }

    public UnaryOperator getOperator() {
        return operator;
    }
}
