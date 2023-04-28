package ast.terms.statements.s.r;

import java.util.ArrayList;
import java.util.List;

public class R implements RBase{
    private final List<R> parallel = new ArrayList<R>();
    public void addR(R r){
        this.parallel.add(r);
    }
}
