package ast.terms;

import java.util.List;

public final class Prog {

    private final String m;
    private final String Aepsilon;
    private final List<Obj> objs;
    public Prog(String m, String aepsilon, List<Obj> objs) {
        this.m = m;
        Aepsilon = aepsilon;
        this.objs = objs;
    }

    public String getM() {
        return m;
    }

    public String getAepsilon() {
        return Aepsilon;
    }

    public List<Obj> getObjs() {
        return objs;
    }

    @Override
    public String toString() {
        return "<"+ m + ',' + Aepsilon + ',' + objs + ">";
    }
}
