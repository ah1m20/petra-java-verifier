package com.cognitionbox.petra.ast.interp.verification;

import com.cognitionbox.petra.ast.interp.Verification;
import com.cognitionbox.petra.ast.interp.junit.tasks.VerificationTask;
import com.cognitionbox.petra.dronesystem.Controller;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@Ignore
@RunWith(Parameterized.class)
public class DroneSystemVerification extends Verification {
    public DroneSystemVerification(VerificationTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(Controller.class);
    }
}
