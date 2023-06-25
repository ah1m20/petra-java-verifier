package ast.interp.junit.tasks;

public class SyntaxInvalid extends BaseVerificationTask {
    private final int errorLine;
    private final String errorMessage;
    public SyntaxInvalid(String objectName, int errorLine, String errorMessage) {
        super(objectName,()->false);
        this.errorLine = errorLine;
        this.errorMessage = errorMessage;
    }

    @Override
        public String toString() {
            return "Syntax:"+getObjectName()+":line"+errorLine+":"+errorMessage;
        }
    }