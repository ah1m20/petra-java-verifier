package com.cognitionbox.petra.meanreversion;

public final class MarketData implements Runnable {
    private final static MarketData marketData = new MarketData();

    private MarketData() {
    }

    public static MarketData getInstance() {
        return marketData;
    }

    private double v = 0;

    public void run() {
        v = Math.random() * 100;
    }

    public boolean x() {return v < 33;}
    public boolean y() {return v >= 33 && v<= 66;}
    public boolean z() {return v > 66;}

}