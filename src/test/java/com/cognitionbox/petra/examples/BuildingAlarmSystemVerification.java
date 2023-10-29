package com.cognitionbox.petra.examples;

import com.cognitionbox.petra.ast.interp.Verification;
import com.cognitionbox.petra.examples.buildingalarmsystem.Building;
import com.cognitionbox.petra.ast.interp.junit.tasks.VerificationTask;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@RunWith(Parameterized.class)
public class BuildingAlarmSystemVerification extends Verification {
    public BuildingAlarmSystemVerification(VerificationTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(Building.class);
    }
}
