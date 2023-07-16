package ast.interp.verification;

import ast.interp.Verification;
import ast.interp.junit.tasks.VerificationTask;
import lightingsystem2.Light;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@RunWith(Parameterized.class)
public class LightingSystem2Verification extends Verification {
    public LightingSystem2Verification(VerificationTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(Light.class);
    }
}
