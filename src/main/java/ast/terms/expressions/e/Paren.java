package ast.terms.expressions.e;

public final class Paren extends EBase {
    public Paren(E inner) {
        super(inner, UnaryOperator.PAREN);
    }
}
