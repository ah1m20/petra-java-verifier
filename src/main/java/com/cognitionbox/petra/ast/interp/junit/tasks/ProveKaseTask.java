package com.cognitionbox.petra.ast.interp.junit.tasks;

import java.util.function.Supplier;

public class ProveKaseTask extends ProveMethodTask {

    private final int caseId;
    public ProveKaseTask(int caseId, String methodName, String objectName, Supplier<Boolean> supplier) {
        super(methodName,objectName, supplier);
        this.caseId = caseId;
    }

    @Override
        public String toString() {
            return "Reachability:"+getObjectName()+":"+getMethodName()+":branch"+caseId;
        }
}