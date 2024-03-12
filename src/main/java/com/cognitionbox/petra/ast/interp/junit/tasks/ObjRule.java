package com.cognitionbox.petra.ast.interp.junit.tasks;

import java.util.function.Supplier;

public class ObjRule extends BaseVerificationTask {

    public ObjRule(String objectName, Supplier<Boolean> supplier) {
        super(objectName,supplier);
    }

    @Override
        public String toString() {
            return getClass().getSimpleName()+":"+getObjectName();
        }
    }