package ast.terms;



import java.util.ArrayList;
import java.util.List;

public final class Obj {
    private final String A;
    private final List<Beta> overlineBeta = new ArrayList<Beta>();
    private final List<Phi> overlinePhi = new ArrayList<Phi>();
    private final List<Delta> overlineDelta = new ArrayList<Delta>();

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

    public List<Beta> getOverlineBeta() {
        return overlineBeta;
    }

    public List<Phi> getOverlinePhi() {
        return overlinePhi;
    }

    public List<Delta> getOverlineDelta() {
        return overlineDelta;
    }
}
