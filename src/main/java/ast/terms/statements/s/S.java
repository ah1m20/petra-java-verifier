package ast.terms.statements.s;

import ast.terms.statements.s.r.R;

import java.util.ArrayList;
import java.util.List;

public final class S {
    private final List<R> sequential = new ArrayList<R>();
    public void addR(R r){
        this.sequential.add(r);
    }
}
