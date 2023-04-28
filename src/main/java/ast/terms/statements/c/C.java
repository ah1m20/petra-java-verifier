package ast.terms.statements.c;

import ast.terms.expressions.PrePost;
import ast.terms.statements.s.S;


public final class C {
    private final PrePost pre;
    private final S s;
    private final PrePost post;

    public C(PrePost pre, S s, PrePost post) {
        this.pre = pre;
        this.s = s;
        this.post = post;
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

    
}
