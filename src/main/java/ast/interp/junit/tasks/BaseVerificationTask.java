package ast.interp.junit.tasks;

import java.util.function.Supplier;

public class BaseVerificationTask implements VerificationTask {
    private final String objectName;
    private final Supplier<Boolean> supplier;
    public BaseVerificationTask(String objectName, Supplier<Boolean> supplier){
        this.objectName = objectName;
        this.supplier = supplier;
    }

    public String getObjectName() {
        return objectName;
    }

    @Override
    public boolean passed() {
        return supplier.get();
    }
}