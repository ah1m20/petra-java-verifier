package ast.interp.verification;

import ast.interp.Verification;
import ast.interp.junit.tasks.VerificationTask;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import simplethermostat.Thermostat;

import java.util.Collection;

@RunWith(Parameterized.class)
public class SimpleThermostatVerification extends Verification {
    public SimpleThermostatVerification(VerificationTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(Thermostat.class);
    }
}
