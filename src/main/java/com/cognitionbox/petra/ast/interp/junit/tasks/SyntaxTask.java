package com.cognitionbox.petra.ast.interp.junit.tasks;

import java.util.concurrent.Callable;

public final class SyntaxTask implements Callable<Boolean> {
    private final String objectName;
    private final int errorLine;
    private final String errorMessage;
    public SyntaxTask(String objectName, int errorLine, String errorMessage) {
        this.objectName = objectName;
        this.errorLine = errorLine;
        this.errorMessage = errorMessage;
    }

    @Override
        public String toString() {
            return getClass().getSimpleName()+":"+objectName+":line"+errorLine+":"+errorMessage;
        }

    @Override
    public Boolean call() throws Exception {
        return false;
    }
}