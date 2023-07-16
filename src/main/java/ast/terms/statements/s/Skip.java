package ast.terms.statements.s;


public final class Skip extends S {
    public Skip(){}
    public Skip(boolean valid, int lineError, String errorMessage) {
        super(valid, lineError, errorMessage);
    }
}
