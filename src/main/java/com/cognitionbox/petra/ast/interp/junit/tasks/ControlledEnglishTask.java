package com.cognitionbox.petra.ast.interp.junit.tasks;

import java.util.function.Supplier;

public class ControlledEnglishTask extends BaseVerificationTask {

    public ControlledEnglishTask(String objectName, Supplier<Boolean> supplier) {
        super(objectName,supplier);
    }

    @Override
        public String toString() {
            return "ControlledEnglish:"+getObjectName();
        }
    }