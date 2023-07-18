package com.cognitionbox.petra.ast.terms;

public class Term {
    private final boolean valid;
    private final int lineError;
    private final String errorMessage;

    public Term(boolean valid, int lineError, String errorMessage) {
        this.valid = valid;
        this.lineError = lineError;
        this.errorMessage = errorMessage;
    }

    public Term() {
        this.valid = true;
        this.lineError = -1;
        this.errorMessage = null;
    }

    public boolean isValid() {
        return valid;
    }

    public int getLineError() {
        return lineError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
