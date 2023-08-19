package com.cognitionbox.petra.examples.tradingsystem;

import com.cognitionbox.petra.ast.terms.External;

@External
public final class MarketDataConnection implements Runnable {
    private final static MarketDataConnection MARKET_DATA_CONNECTION = new MarketDataConnection();

    private MarketDataConnection() {
    }

    public static MarketDataConnection getInstance() {
        return MARKET_DATA_CONNECTION;
    }

    private double v = 0;

    public void run() {
        v = Math.random() * 100;
    }

    public boolean x() {return v < 33;}
    public boolean y() {return v >= 33 && v<= 66;}
    public boolean z() {return v > 66;}

}