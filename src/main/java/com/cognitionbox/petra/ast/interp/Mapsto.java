package com.cognitionbox.petra.ast.interp;

public class Mapsto<A,B> {
    private final A from;
    private final B too;

    public Mapsto(A from, B too) {
        this.from = from;
        this.too = too;
    }

    public A getFrom() {
        return from;
    }

    public B getToo() {
        return too;
    }

    @Override
    public String toString() {
        return from + " \\mapsto " +too;
    }
}