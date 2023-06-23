package droneroutesystem;

import ast.terms.Initial;

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
