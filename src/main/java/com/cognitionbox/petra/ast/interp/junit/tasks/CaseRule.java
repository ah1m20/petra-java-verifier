package com.cognitionbox.petra.ast.interp.junit.tasks;

import com.cognitionbox.petra.ast.interp.Symbolic;
import com.cognitionbox.petra.ast.terms.Delta;
import com.cognitionbox.petra.ast.terms.Obj;
import com.cognitionbox.petra.ast.terms.statements.c.CUnary;

public final class CaseRule extends SemanticsTask {
    private final Obj o;
    private final Delta d;
    private final CUnary c;

    public CaseRule(int sequenceNo, Symbolic symbolic, Obj o, Delta d, CUnary c) {
        super(sequenceNo,symbolic);
        this.o = o;
        this.d = d;
        this.c = c;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+":"+o.getA()+":"+d.getM()+":branch"+c.getId();
    }

    @Override
    public Boolean call() throws Exception {
        return symbolic.interpC(d.getM(), c, o).isPresent();
    }
}