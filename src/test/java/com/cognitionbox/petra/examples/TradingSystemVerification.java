package com.cognitionbox.petra.examples;

import com.cognitionbox.petra.ast.interp.Verification;
import com.cognitionbox.petra.ast.interp.junit.tasks.VerificationTask;
import com.cognitionbox.petra.examples.tradingsystem.MacroStrategy;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@RunWith(Parameterized.class)
public class TradingSystemVerification extends Verification {
    public TradingSystemVerification(VerificationTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(MacroStrategy.class);
    }
}
