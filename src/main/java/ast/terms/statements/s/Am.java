package ast.terms.statements.s;

import java.util.Objects;


public final class Am implements S {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Am am = (Am) o;
        return Objects.equals(a, am.a) && Objects.equals(m, am.m);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, m);
    }
}
