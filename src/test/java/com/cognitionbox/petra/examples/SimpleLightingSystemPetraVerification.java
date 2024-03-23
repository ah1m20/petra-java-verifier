package com.cognitionbox.petra.examples;

import com.cognitionbox.petra.ast.interp.PetraVerification;
import com.cognitionbox.petra.ast.interp.junit.tasks.PetraTask;
import com.cognitionbox.petra.examples.simplelightsystem.Light;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

//@RunWith(PetraVerificationRunner.class)
@RunWith(Parameterized.class)
public class SimpleLightingSystemPetraVerification extends PetraVerification {
    public SimpleLightingSystemPetraVerification(PetraTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(Light.class);
    }
}
