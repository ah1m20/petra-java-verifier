package ast.interp.junit.tasks;

import java.util.function.Supplier;

public class ProveEntryPointTask extends BaseVerificationTask {

    public ProveEntryPointTask(String objectName, Supplier<Boolean> supplier) {
        super(objectName,supplier);
    }

    @Override
        public String toString() {
            return "EntryPoint:"+getObjectName();
        }
    }