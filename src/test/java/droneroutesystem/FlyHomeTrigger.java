package droneroutesystem;

import ast.terms.Initial;

public class FlyHomeTrigger {

    private final Wifi wifi  = new Wifi();
    private final Battery battery  = new Battery();


    public boolean enabled(){
        return wifi.lowSNR() || battery.returnHomeLevel();
    }

    @Initial
    public boolean disabled(){
        return !(wifi.lowSNR() || battery.returnHomeLevel());
    }
}
