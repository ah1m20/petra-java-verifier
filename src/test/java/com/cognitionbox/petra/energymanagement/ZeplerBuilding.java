package com.cognitionbox.petra.energymanagement;

public class ZeplerBuilding {

    private final ComputerRoom computerRoom = new ComputerRoom();

    public boolean highUsage(){ return computerRoom.highUsage(); }
    public boolean lowUsage(){ return computerRoom.lowUsage(); }
}
