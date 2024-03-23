package com.cognitionbox.petra.ast.interp.junit.tasks;

import com.cognitionbox.petra.ast.interp.Symbolic;
import com.cognitionbox.petra.ast.terms.Obj;

public final class ObjRule extends SemanticsTask {
    private final Obj o;
    public ObjRule(int sequenceNumber, Symbolic symbolic, Obj o) {
        super(sequenceNumber, symbolic);
        this.o = o;
    }

    @Override
        public String toString() {
            return getClass().getSimpleName()+":"+o.getA();
        }

    @Override
    public Boolean call() throws Exception {
        return symbolic.interpObj(o).isPresent();
    }
}