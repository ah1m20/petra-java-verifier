package ast.interp.junit.tasks;

import java.util.function.Supplier;

public class ProveMethodTask extends BaseVerificationTask {

    private final String methodName;
    public ProveMethodTask(String methodName, String objectName, Supplier<Boolean> supplier) {
        super(objectName, supplier);
        this.methodName = methodName;
    }

    @Override
    public String toString() {
            return "Reachability:"+getObjectName()+":"+methodName;
        }

    public String getMethodName() {
        return methodName;
    }
}