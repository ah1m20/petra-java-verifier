package com.cognitionbox.petra.ast.interp;

public class Mapsto<A,B> {
    private final A from;
    private final B to;

    public Mapsto(A from, B to) {
        this.from = from;
        this.to = to;
    }

    public A getFrom() {
        return from;
    }

    public B getTo() {
        return to;
    }

    @Override
    public String toString() {
        return from + " \\mapsto " + to;
    }
}