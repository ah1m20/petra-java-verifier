package ast.terms;



import java.util.ArrayList;
import java.util.List;

public final class Obj {
    private final String A;
    private final List<Beta> overline_beta = new ArrayList<Beta>();
    private final List<Phi> overline_phi = new ArrayList<Phi>();
    private final List<Delta> overline_delta = new ArrayList<Delta>();

    public Obj(String A) {
        this.A = A;
    }

    public void addBeta(Beta b){
        this.overline_beta.add(b);
    }

    public void addPhi(Phi p){
        this.overline_phi.add(p);
    }

    public void addDelta(Delta d){
        this.overline_delta.add(d);
    }

    
}
