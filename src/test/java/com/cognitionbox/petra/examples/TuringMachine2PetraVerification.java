package com.cognitionbox.petra.examples;

import com.cognitionbox.petra.ast.interp.PetraVerification;
import com.cognitionbox.petra.ast.interp.junit.tasks.PetraTask;
import com.cognitionbox.petra.examples.turingmachine2.TuringMachine;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@Ignore
@RunWith(Parameterized.class)
public class TuringMachine2PetraVerification extends PetraVerification {
    public TuringMachine2PetraVerification(PetraTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(TuringMachine.class);
    }
}
