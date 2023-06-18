package energymanagement;


import ast.terms.Base;

@Base public class AirConditioners {

    private final Sensors sensors = Sensors.getInstance();

    public boolean highUsage(){ return sensors.highAirConEnergy(); }
    public boolean lowUsage(){ return sensors.lowAirConEnergy(); }

}
