package simplethermostat;

import static ast.interp.util.Program.startReactive;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        startReactive(0,new Thermostat(), Sensor.getInstance());
    }
}
