package ast.terms.statements.s.r;

public final class Am implements RBase {
    private final String a;
    private final String m;

    public Am(String a, String m) {
        this.a = a;
        this.m = m;
    }

    public String getA() {
        return a;
    }

    public String getM() {
        return m;
    }
}
