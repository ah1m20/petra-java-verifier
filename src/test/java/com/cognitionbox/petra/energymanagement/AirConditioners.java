package com.cognitionbox.petra.energymanagement;


import com.cognitionbox.petra.ast.terms.Base;

@Base public class AirConditioners {

    private final Sensors sensors = Sensors.getInstance();

    public boolean highUsage(){ return sensors.highAirConEnergy(); }
    public boolean lowUsage(){ return sensors.lowAirConEnergy(); }

}
