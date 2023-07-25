package com.cognitionbox.petra.energymanagement;

public class Floor1 {

    private final AirConditioners airConditioners = new AirConditioners();
    private final Appliances appliances = new Appliances();
    private final Heaters heaters = new Heaters();
    private final Plumbing plumbing = new Plumbing();


    public boolean lowUsage(){
        return heaters.lowUsage() && airConditioners.lowUsage() && appliances.lowUsage() && plumbing.lowUsage();
    }
    public boolean highUsage(){
        return heaters.highUsage() && airConditioners.highUsage() && appliances.highUsage() && plumbing.highUsage();
    }

}
