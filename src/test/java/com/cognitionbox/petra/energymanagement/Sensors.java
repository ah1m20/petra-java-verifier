package com.cognitionbox.petra.energymanagement;

public final class Sensors implements Runnable {
    private final static Sensors sensors = new Sensors();

    private Sensors() {
    }

    public static Sensors getInstance() {
        return sensors;
    }

    private double totalAppliancesEnergy = 0;
    private double totalHeaterEnergy = 0;
    private double totalAirConEnergy = 0;
    private double totalComputerEnergy = 0;
    private double totalPlumbingEnergy = 0;

    public void run() {
        totalAppliancesEnergy = Math.random() * 1000;
        totalHeaterEnergy = Math.random() * 1000;
        totalAirConEnergy = Math.random() * 1000;
        totalComputerEnergy = Math.random() * 1000;
        totalPlumbingEnergy = Math.random() * 1000;
    }
    public boolean highAppliancesEnergy() {return totalAppliancesEnergy > 750;}
    public boolean lowAppliancesEnergy() {return totalAppliancesEnergy <= 750;}
    public boolean highHeaterEnergy() {return totalHeaterEnergy > 750;}
    public boolean lowHeaterEnergy() {return totalHeaterEnergy <= 750;}
    public boolean highAirConEnergy() {return totalAirConEnergy > 750;}
    public boolean lowAirConEnergy() {return totalAirConEnergy <= 750;}
    public boolean highComputerEnergy() {return totalComputerEnergy > 750;}
    public boolean lowComputerEnergy() {return totalComputerEnergy <= 750;}
    public boolean highPlumbingEnergy() {return totalPlumbingEnergy > 750;}
    public boolean lowPlumbingEnergy() {return totalPlumbingEnergy <= 750;}

}