package com.cognitionbox.petra.ast.terms.statements.c;

public final class CBinary extends C {
    private final C left;
    private final C right;

    public CBinary(int id, C left, C right) {
        super(id,true,-1,null);
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left + "+" + right;
    }

    public C getLeft() {
        return left;
    }

    public C getRight() {
        return right;
    }
}
