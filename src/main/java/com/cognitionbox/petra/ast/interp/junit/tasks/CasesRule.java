package com.cognitionbox.petra.ast.interp.junit.tasks;

import java.util.function.Supplier;

public class CasesRule extends BaseVerificationTask {

    private final String methodName;
    public CasesRule(String methodName, String objectName, Supplier<Boolean> supplier) {
        super(objectName, supplier);
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+":"+getObjectName()+":"+methodName;
    }

    public String getMethodName() {
        return methodName;
    }
}