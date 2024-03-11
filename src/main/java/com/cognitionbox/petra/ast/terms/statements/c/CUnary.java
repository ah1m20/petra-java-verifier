package com.cognitionbox.petra.ast.terms.statements.c;

import com.cognitionbox.petra.ast.terms.expressions.PrePost;
import com.cognitionbox.petra.ast.terms.statements.s.S;


public final class CUnary extends C {
    private final PrePost pre;
    private final S s;
    private final PrePost post;

    public CUnary(int id, PrePost pre, S s, PrePost post) {
        super(id,true,-1,null);
        this.pre = pre;
        this.s = s;
        this.post = post;
    }

    public CUnary(int id, boolean valid, int lineError, String errorMessage) {
        super(id,valid, lineError, errorMessage);
        this.pre = null;
        this.s = null;
        this.post = null;
    }

    public PrePost getPre() {
        return pre;
    }

    public S getS() {
        return s;
    }

    public PrePost getPost() {
        return post;
    }

    @Override
    public String toString() {
        return "{" + pre +
                "}" + s +
                "{" + post +"}";
    }
}
