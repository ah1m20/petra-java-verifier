package com.cognitionbox.petra.ast.interp.junit.tasks;

import java.util.concurrent.Callable;

public abstract class PetraTask implements Comparable<PetraTask>, Callable<Boolean> {
    final int sequenceNumber;

    public PetraTask(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public int compareTo(PetraTask task) {
        return Integer.compare(this.sequenceNumber,task.sequenceNumber);
    }
}