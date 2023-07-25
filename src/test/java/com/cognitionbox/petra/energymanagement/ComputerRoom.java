package com.cognitionbox.petra.energymanagement;




public class ComputerRoom {

    private final ComputerNetwork computerNetwork = new ComputerNetwork();

    public boolean lowUsage(){
        return computerNetwork.lowUsage();
    }
    public boolean highUsage(){
        return computerNetwork.highUsage();
    }

}
