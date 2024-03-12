package com.cognitionbox.petra.ast.interp.junit.tasks;

import java.util.function.Supplier;

public class ControlledEnglish extends BaseVerificationTask {

    public ControlledEnglish(String objectName, Supplier<Boolean> supplier) {
        super(objectName,supplier);
    }

    @Override
        public String toString() {
            return getClass().getSimpleName()+":"+getObjectName();
        }
    }