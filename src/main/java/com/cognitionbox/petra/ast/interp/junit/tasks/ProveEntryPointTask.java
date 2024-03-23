package com.cognitionbox.petra.ast.interp.junit.tasks;

import com.cognitionbox.petra.ast.interp.Symbolic;
import com.cognitionbox.petra.ast.terms.Prog;

public final class ProveEntryPointTask extends SemanticsTask {
    private final Prog prog;

    public ProveEntryPointTask(int sequenceNumber, Symbolic symbolic, Prog prog) {
        super(sequenceNumber, symbolic);
        this.prog = prog;
    }
    @Override
        public String toString() {
            return getClass().getSimpleName()+":"+prog.getAepsilon();
        }

    @Override
    public Boolean call() throws Exception {
        return symbolic.interpProg(prog).isPresent();
    }
}