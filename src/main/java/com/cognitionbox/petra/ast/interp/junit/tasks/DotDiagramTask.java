package com.cognitionbox.petra.ast.interp.junit.tasks;

import com.cognitionbox.petra.ast.interp.Symbolic;
import com.cognitionbox.petra.ast.terms.Obj;

public final class DotDiagramTask extends SemanticsTask {
    private final Obj o;
    public DotDiagramTask(Symbolic symbolic, Obj o) {
        super(Integer.MAX_VALUE,symbolic);
        this.o = o;
    }

    @Override
    public Boolean call() throws Exception {
        symbolic.printDigraph(o);
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+":"+o.getA();
    }
}