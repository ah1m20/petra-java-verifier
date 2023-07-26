package com.cognitionbox.petra.examples.simplethermostat;

import com.cognitionbox.petra.ast.terms.Initial;

public class Thermostat implements Runnable {

    private final Logging logging = new Logging();
    private final Temperature temperature = new Temperature();
    private final Control control = new Control();

    @Initial public boolean OffAndBelowTarget(){
        return control.off() && temperature.belowTarget();
    }
    public boolean OffAndOnOrAboveTarget(){
        return control.off() && temperature.aboveOrEqualToTarget();
    }
    public boolean OnAndBelowTarget(){
        return control.on() && temperature.belowTarget();
    }
    public boolean OnAndOnOrAboveTarget(){
        return control.on() && temperature.aboveOrEqualToTarget();
    }

    public void run(){
        if (OffAndBelowTarget()){
            logging.logOffAndBelowTarget();
            control.turnOn();
            assert(OnAndBelowTarget() ^ OnAndOnOrAboveTarget());
        } else if (OffAndOnOrAboveTarget()){
            logging.logOffAndOnOrAboveTarget();
            assert(OffAndBelowTarget() ^ OffAndOnOrAboveTarget());
        } else if (OnAndBelowTarget()){
            logging.logOnAndBelowTarget();
            assert(OnAndBelowTarget() ^ OnAndOnOrAboveTarget());
        } else if (OnAndOnOrAboveTarget()){
            logging.logOnAndOnOrAboveTarget();
            control.turnOff();
            assert(OffAndBelowTarget() ^ OffAndOnOrAboveTarget());
        }
    }
}
