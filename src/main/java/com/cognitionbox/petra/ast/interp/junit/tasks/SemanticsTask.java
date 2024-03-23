package com.cognitionbox.petra.ast.interp.junit.tasks;

import com.cognitionbox.petra.ast.interp.Symbolic;

public abstract class SemanticsTask extends PetraTask {

    protected final Symbolic symbolic;

    public SemanticsTask(int sequenceNumber, Symbolic symbolic) {
        super(sequenceNumber);
        this.symbolic = symbolic;
    }
}