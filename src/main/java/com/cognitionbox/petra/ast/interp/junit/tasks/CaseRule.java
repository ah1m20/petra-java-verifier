package com.cognitionbox.petra.ast.interp.junit.tasks;

import java.util.function.Supplier;

public class CaseRule extends CasesRule {

    private final int caseId;
    public CaseRule(int caseId, String methodName, String objectName, Supplier<Boolean> supplier) {
        super(methodName,objectName, supplier);
        this.caseId = caseId;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+":"+getObjectName()+":"+getMethodName()+":branch"+caseId;
    }
}