package ast.terms.statements.s.r;



import java.util.ArrayList;
import java.util.List;

public class Par implements R {
    private final List<RTerminal> parallel = new ArrayList<>();
    public void addR(RTerminal r){
        this.parallel.add(r);
    }

    
}