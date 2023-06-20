package droneroutesystem;

import ast.terms.Initial;

public class FlyHome {

    private final Wifi wifi  = new Wifi();
    private final Battery battery  = new Battery();


    public boolean flyHome(){
        return wifi.lowSNR() || battery.returnHomeLevel();
    }

    @Initial
    public boolean notFlyHome(){
        return !(wifi.lowSNR() || battery.returnHomeLevel());
    }
}
