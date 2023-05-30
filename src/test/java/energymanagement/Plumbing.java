package energymanagement;

import ast.terms.Base;

@Base public class Plumbing {

    private double totalEnergy;
    private final double maxEnergyConsumption = 200;

    public boolean highUsage(){ return maxEnergyConsumption*Math.random() > 100; }
    public boolean lowUsage(){ return !highUsage();}

}
