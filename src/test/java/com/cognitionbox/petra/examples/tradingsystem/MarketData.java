package com.cognitionbox.petra.examples.tradingsystem;

import com.cognitionbox.petra.ast.terms.External;

@External
public final class MarketData {
    private final String symbol;
    private final double bidPrice;
    private final double askPrice;
    private final long timestamp;

    public MarketData(String symbol, double bidPrice, double askPrice, long timestamp) {
        this.symbol = symbol;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
