package ast.terms.expressions.e;



public enum BinaryOperator {
    AND,
    OR,
    XOR;

    @Override
    public String toString() {
        return "\""+this.name()+"\"";
    }
}
