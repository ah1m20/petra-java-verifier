package com.cognitionbox.petra.examples;

import com.cognitionbox.petra.ast.interp.PetraVerification;
import com.cognitionbox.petra.ast.interp.junit.tasks.PetraTask;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import com.cognitionbox.petra.examples.turingmachine.TuringMachine;
import java.util.Collection;

@Ignore
@RunWith(Parameterized.class)
public class TuringMachinePetraVerification extends PetraVerification {
    public TuringMachinePetraVerification(PetraTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(TuringMachine.class);
    }
}
