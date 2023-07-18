package com.cognitionbox.petra.energymanagement;


import com.cognitionbox.petra.ast.terms.Base;

@Base
public class Heaters {
    private final Sensors sensors = Sensors.getInstance();
    public boolean highUsage(){ return sensors.highHeaterEnergy(); }
    public boolean lowUsage(){ return sensors.lowHeaterEnergy(); }

}
