package ast.terms.expressions.e;



public enum BinaryOperator {
    AND,
    OR,
    XOR,
    UNKOWN;

    @Override
    public String toString() {
        return "\""+this.name()+"\"";
    }
}
