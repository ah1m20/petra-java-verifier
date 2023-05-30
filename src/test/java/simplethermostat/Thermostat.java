package simplethermostat;

public class Thermostat {

    private final Logging logging = new Logging();
    private final Temperature temperature = new Temperature();
    private final Control control = new Control();

    public boolean OffAndBelowTarget(){
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
    public void action(){
        if (OffAndBelowTarget()){
            logging.logOffAndBelowTarget();
            control.turnOn();
            assert(OnAndBelowTarget() ^ OnAndOnOrAboveTarget());
        }
        if (OffAndOnOrAboveTarget()){
            logging.logOffAndOnOrAboveTarget();
            assert(OffAndBelowTarget() ^ OffAndOnOrAboveTarget());
        }
        if (OnAndBelowTarget()){
            logging.logOnAndBelowTarget();
            assert(OnAndBelowTarget() ^ OnAndOnOrAboveTarget());
        }
        if (OnAndOnOrAboveTarget()){
            logging.logOnAndOnOrAboveTarget();
            control.turnOff();
            assert(OffAndBelowTarget() ^ OffAndOnOrAboveTarget());
        }
    }
}
