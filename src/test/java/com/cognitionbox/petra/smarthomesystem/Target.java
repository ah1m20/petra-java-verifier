package com.cognitionbox.petra.smarthomesystem;

public class Target {

    Control control = new Control();
    private volatile float target = 0;
    private volatile boolean updated = false;
    public boolean targetUpdated() { return updated; }
    public boolean targetNotUpdated() { return !updated; }

    public void readTarget() {
        if (targetNotUpdated()){
            target = control.getTarget();
            updated = true;
            assert(targetUpdated());
        }
    }
}
