package com.cognitionbox.petra.droneroutesystem;

import com.cognitionbox.petra.ast.terms.Initial;

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
