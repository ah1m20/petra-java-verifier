package energymanagement;


import droneroutesystem.Controller;
import droneroutesystem.DroneConnection;

import static ast.interp.util.Program.mainLoop;

public class EnergyManagementMain {
    public static void main(String[] args) {
        mainLoop(500,new EnergyManagement(), Sensors.getInstance());
    }
}
