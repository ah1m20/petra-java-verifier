package ast.terms.statements.s.r;

import ast.terms.statements.s.S;

public final class Skip implements S, RTerminal {
    @Override
    public String toString() {
        return "skip";
    }
}
