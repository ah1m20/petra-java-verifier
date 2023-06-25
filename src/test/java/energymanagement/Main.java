package energymanagement;


import static ast.interp.util.Program.startReactive;

public class Main {
    public static void main(String[] args) {
        startReactive(500,new EnergyManagement(), Sensors.getInstance());
    }
}
