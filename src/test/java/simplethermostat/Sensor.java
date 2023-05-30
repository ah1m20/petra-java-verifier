package simplethermostat;

import ast.terms.Base;

public class Sensor {
    private volatile int target = 20;

    private int getTemp() {
        return (int)(Math.random()*100);
    }

    public void setTarget(int target) {
        target = target;
    }

    public boolean aboveOrEqualToTarget() { return getTemp() > target; }
    public boolean belowTarget() { return getTemp() <= target; }
}
