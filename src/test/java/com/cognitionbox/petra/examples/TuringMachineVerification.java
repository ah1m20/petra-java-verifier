package com.cognitionbox.petra.examples;

import com.cognitionbox.petra.ast.interp.Verification;
import com.cognitionbox.petra.ast.interp.junit.tasks.VerificationTask;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import com.cognitionbox.petra.examples.turingmachine.TuringMachine;
import java.util.Collection;

@Ignore
@RunWith(Parameterized.class)
public class TuringMachineVerification extends Verification {
    public TuringMachineVerification(VerificationTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(TuringMachine.class);
    }
}
