package ast.interp.verification;

import ast.interp.Verification;
import ast.interp.junit.tasks.VerificationTask;
import lightsystem.Light;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@RunWith(Parameterized.class)
public class LightingSystemVerification extends Verification {
    public LightingSystemVerification(VerificationTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(Light.class);
    }
}
