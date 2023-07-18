package com.cognitionbox.petra.dronesystem;

public final class Land {
    private final Temperature temperature  = new Temperature();

    public boolean land(){
        return temperature.high();
    }

    public boolean notLand(){
        return !temperature.high();
    }
}
