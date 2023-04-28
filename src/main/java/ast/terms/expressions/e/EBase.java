package ast.terms.expressions.e;

public class EBase implements E{
    private final E inner;
    private final UnaryOperator operator;
    public EBase(E inner, UnaryOperator operator) {
        this.inner = inner;
        this.operator = operator;
    }
}
