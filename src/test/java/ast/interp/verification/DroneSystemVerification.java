package ast.interp.verification;

import ast.interp.Verification;
import ast.interp.junit.tasks.VerificationTask;
import dronesystem.Controller;
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
