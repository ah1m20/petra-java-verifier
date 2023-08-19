package com.cognitionbox.petra.examples.tradingsystem;

import com.cognitionbox.petra.ast.terms.External;

@External
public final class Order {
    private final String orderId;
    private final String symbol;
    private final double price;
    private final int quantity;
    private final String orderType;  // e.g., MARKET, LIMIT

    public Order(String orderId, String symbol, double price, int quantity, String orderType) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.orderType = orderType;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrderType() {
        return orderType;
    }

}
