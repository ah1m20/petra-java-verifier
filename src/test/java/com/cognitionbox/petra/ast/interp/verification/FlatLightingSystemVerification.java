package com.cognitionbox.petra.ast.interp.verification;

import com.cognitionbox.petra.ast.interp.Verification;
import com.cognitionbox.petra.flatlightingsystem.Flat;
import com.cognitionbox.petra.ast.interp.junit.tasks.VerificationTask;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@RunWith(Parameterized.class)
public class FlatLightingSystemVerification extends Verification {
    public FlatLightingSystemVerification(VerificationTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(Flat.class);
    }
}
