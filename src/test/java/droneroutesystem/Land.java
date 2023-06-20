package droneroutesystem;

import ast.terms.Initial;

public final class Land {
    private final Temperature temperature  = new Temperature();

    public boolean land(){
        return temperature.high();
    }

    @Initial
    public boolean notLand(){
        return !temperature.high();
    }
}
