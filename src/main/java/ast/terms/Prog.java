package ast.terms;

import java.util.List;

public class Prog {
    private final boolean reactive;
    private final String m;
    private final String Aepsilon;
    private final List<Obj> objs;
    public Prog(boolean reactive, String m, String aepsilon, List<Obj> objs) {
        this.reactive = reactive;
        this.m = m;
        this.Aepsilon = aepsilon;
        this.objs = objs;
    }

    public Prog(String m, String aepsilon, List<Obj> objs) {
        this(false,m,aepsilon,objs);
    }

    public boolean isReactive() {
        return reactive;
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
