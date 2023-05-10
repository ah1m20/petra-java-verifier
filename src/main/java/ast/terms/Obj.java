package ast.terms;
import ast.interp.util.Set;
public final class Obj {
    private final String A;
    private final Set<Beta> overlineBeta = new Set<>();
    private final Set<Phi> overlinePhi = new Set<>();
    private final Set<Delta> overlineDelta = new Set<>();

    public Obj(String A) {
        this.A = A;
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

    public Set<Beta> getOverlineBeta() {
        return overlineBeta;
    }

    public Set<Phi> getOverlinePhi() {
        return overlinePhi;
    }

    public Set<Delta> getOverlineDelta() {
        return overlineDelta;
    }
}
