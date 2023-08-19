package com.cognitionbox.petra.examples.tradingsystem;

import com.cognitionbox.petra.ast.terms.External;

@External
public final class Trade {
    private final String tradeId;
    private final String orderId;
    private final String symbol;
    private final double executedPrice;
    private final int executedQuantity;
    private final long timestamp;

    public Trade(String tradeId, String orderId, String symbol, double executedPrice, int executedQuantity, long timestamp) {
        this.tradeId = tradeId;
        this.orderId = orderId;
        this.symbol = symbol;
        this.executedPrice = executedPrice;
        this.executedQuantity = executedQuantity;
        this.timestamp = timestamp;
    }

    public String getTradeId() {
        return tradeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getExecutedPrice() {
        return executedPrice;
    }

    public int getExecutedQuantity() {
        return executedQuantity;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
