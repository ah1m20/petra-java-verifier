package com.cognitionbox.petra.ast.terms.statements.c;

import com.cognitionbox.petra.ast.terms.Term;
import com.cognitionbox.petra.ast.terms.expressions.PrePost;
import com.cognitionbox.petra.ast.terms.statements.s.S;


public final class C extends Term {
    private final int id;
    private final PrePost pre;
    private final S s;
    private final PrePost post;

    public C(int id, PrePost pre, S s, PrePost post) {
        this.id = id;
        this.pre = pre;
        this.s = s;
        this.post = post;
    }

    public C(int id, boolean valid, int lineError, String errorMessage) {
        super(valid, lineError, errorMessage);
        this.id = id;
        this.pre = null;
        this.s = null;
        this.post = null;
    }

    public int getId() {
        return id;
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
