package ast.terms;
import ast.interp.util.Set;

import java.util.ArrayList;
import java.util.List;

public final class Obj {
    private final String A;
    private final List<Beta> overlineBeta = new ArrayList<>();
    private final List<Phi> overlinePhi = new ArrayList<>();
    private final List<Delta> overlineDelta = new ArrayList<>();

    private final boolean isPrimitive;

    public Obj(String A, boolean isPrimitive) {
        this.A = A;
        this.isPrimitive = isPrimitive;
    }

    public void addBeta(Beta b){
        this.overlineBeta.add(b);
    }

    public void addPhi(Phi p){
        this.overlinePhi.add(p);
    }

    public void addDelta(Delta d){
        this.overlineDelta.add(d);
    }

    public String getA() {
        return A;
    }

    public List<Beta> getOverlineBeta() {
        return overlineBeta;
    }

    public List<Phi> getOverlinePhi() {
        return overlinePhi;
    }

    public List<Delta> getOverlineDelta() {
        return overlineDelta;
    }

    public boolean isPrimitive() {
        return isPrimitive;
    }
}
