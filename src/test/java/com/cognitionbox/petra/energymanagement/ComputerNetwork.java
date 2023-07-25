package com.cognitionbox.petra.energymanagement;


import com.cognitionbox.petra.ast.terms.Base;

@Base
public class ComputerNetwork {
    private final Sensors sensors = Sensors.getInstance();
    public boolean highUsage(){ return sensors.highComputerEnergy(); }
    public boolean lowUsage(){ return sensors.lowComputerEnergy(); }

}
