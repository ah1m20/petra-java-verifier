package com.cognitionbox.petra.ast.interp.junit.tasks;

import com.cognitionbox.petra.ast.interp.Symbolic;
import com.cognitionbox.petra.ast.terms.Delta;
import com.cognitionbox.petra.ast.terms.Obj;

public final class CasesRule extends SemanticsTask {
    private final Obj o;
    private final Delta d;

    public CasesRule(int sequenceNumber, Symbolic symbolic, Obj o, Delta d) {
        super(sequenceNumber,symbolic);
        this.o = o;
        this.d = d;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+":"+o.getA()+":"+d.getM();
    }

    @Override
    public Boolean call() throws Exception {
        return symbolic.interpOverlineC(d.getM(),symbolic.lookupM(d.getM(),o),o).isPresent();
    }
}