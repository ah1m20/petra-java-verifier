package com.cognitionbox.petra.examples.droneroutesystem;

import com.cognitionbox.petra.ast.terms.Initial;

public final class TemperatureTrigger {
    private final Temperature temperature  = new Temperature();

    public boolean enabled(){
        return temperature.high();
    }

    @Initial
    public boolean disabled(){
        return !temperature.high();
    }
}
