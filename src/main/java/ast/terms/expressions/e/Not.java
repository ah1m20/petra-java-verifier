package ast.terms.expressions.e;

public final class Not extends EBase {
    public Not(E inner) {
        super(inner, UnaryOperator.NOT);
    }
}
