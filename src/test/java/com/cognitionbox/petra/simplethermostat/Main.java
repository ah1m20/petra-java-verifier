package com.cognitionbox.petra.simplethermostat;

import static com.cognitionbox.petra.ast.interp.util.Program.startReactive;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        startReactive(0,new Thermostat(), Sensor.getInstance());
    }
}
