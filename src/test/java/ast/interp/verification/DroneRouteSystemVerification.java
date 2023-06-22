package ast.interp.verification;

import ast.interp.Verification;
import ast.interp.junit.tasks.VerificationTask;
import droneroutesystem.Controller;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@RunWith(Parameterized.class)
public class DroneRouteSystemVerification extends Verification {
    public DroneRouteSystemVerification(VerificationTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(Controller.class);
    }
}
