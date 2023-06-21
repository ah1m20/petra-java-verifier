package ast.interp.junit.tasks;

import java.util.function.Supplier;

public class ProveSoundnessAndCompletenessTask extends BaseVerificationTask {

    public ProveSoundnessAndCompletenessTask(String objectName, Supplier<Boolean> supplier) {
        super(objectName,supplier);
    }

    @Override
        public String toString() {
            return "Soundness:"+getObjectName();
        }
    }