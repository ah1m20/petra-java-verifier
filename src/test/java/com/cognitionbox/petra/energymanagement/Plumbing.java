package com.cognitionbox.petra.energymanagement;

import com.cognitionbox.petra.ast.terms.Base;

@Base public class Plumbing {

    private final Sensors sensors = Sensors.getInstance();

    public boolean highUsage(){ return sensors.highPlumbingEnergy(); }
    public boolean lowUsage(){ return sensors.highPlumbingEnergy(); }

}
