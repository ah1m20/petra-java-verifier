package com.cognitionbox.petra.ast.interp.junit.tasks;

public class Syntax extends BaseVerificationTask {
    private final int errorLine;
    private final String errorMessage;
    public Syntax(String objectName, int errorLine, String errorMessage) {
        super(objectName,()->false);
        this.errorLine = errorLine;
        this.errorMessage = errorMessage;
    }

    @Override
        public String toString() {
            return getClass().getSimpleName()+":"+getObjectName()+":line"+errorLine+":"+errorMessage;
        }
    }