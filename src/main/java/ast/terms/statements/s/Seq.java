package ast.terms.statements.s;

import ast.terms.statements.s.r.R;


import java.util.List;

public final class Seq implements S{
    private final List<R> sequential;
    public Seq(List<R> sequential) {
        this.sequential = sequential;
    }
}