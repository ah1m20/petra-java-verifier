package com.cognitionbox.petra.energymanagement;


import com.cognitionbox.petra.ast.terms.Base;

@Base public class Appliances {
    private final Sensors sensors = Sensors.getInstance();
    public boolean highUsage(){ return sensors.highAppliancesEnergy(); }
    public boolean lowUsage(){ return sensors.lowAppliancesEnergy(); }
}
