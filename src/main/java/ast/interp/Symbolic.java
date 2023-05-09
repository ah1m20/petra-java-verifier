package ast.interp;

import ast.interp.util.Ops;

import static ast.interp.util.Collections.set;
import static ast.interp.util.Collections.list;
import static ast.interp.util.Collections.forEach;
import static ast.interp.util.Collections.forall;

import ast.terms.*;
import ast.terms.expressions.e.E;
import ast.terms.statements.c.C;
import ast.terms.statements.s.S;
import com.google.common.collect.Sets;
import java.util.*;

public final class Symbolic {
    private final ObjectTable objectTable = new ObjectTable();

    public Symbolic(Prog prog){
        for (Obj obj : prog.getObjs()){
            objectTable.put(obj.getA(),obj);
        }
    }

    public Set<String> Theta(Obj A){
        return set(A.getOverlinePhi(), (e->e.getP()));
    }

    private Obj objectDef(Beta beta){
        return objectTable.lookup(beta.getObjectId());
    }
    public IObj interp(Obj A){
        List<Set<String>> Thetas = list(A.getOverlineBeta(), beta->Theta(objectDef(beta)) );
        Set<List<String>> Omega = Ops.product(Thetas);
        return new IObj(Omega,interp(A.getOverlinePhi()),interpDeltas(A.getOverlineDelta(),A));
    }

    public Map<String, E> interp(List<Phi> overlinePhi){
        Map<String, E> record = new HashMap<>();
        forEach(overlinePhi, phi->record.put(phi.getP(),phi.getE()));
        return record;
    }

    public Map<String, Optional<Func<String>>> interpDeltas(List<Delta> overlineDelta, Obj A){
        Map<String, Optional<Func<String>>> record = new HashMap<>();
        forEach(overlineDelta, delta->record.put(delta.getM(),interp(delta.getOverlineC(),A)));
        return record;
    }

    public Optional<Func<String>> interp(List<C> ovelineC, Obj A){
        if (forall(ovelineC,c->interp(c,A).isPresent()) && pairwiseDisjoint(ovelineC,A)){
            return Optional.of(Ops.functionUnion(list(ovelineC, c->interp(c,A).get())));
        } else {
            return Optional.empty();
        }
    }

    private boolean pairwiseDisjoint(List<C> ovelineC, Obj A) {
        for (C i : ovelineC){
            for (C j : ovelineC){
                if (i!=j){
                    Optional<Func<String>> a = interp(i,A);
                    Optional<Func<String>> b = interp(j,A);
                    if (Sets.intersection(a.get().dom(),b.get().dom()).size()!=0){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // TODO
    public Optional<Func<String>> interp(C c, Obj A){
        Func<String> f = null;
        if (interp(c.getS(),A).isPresent() && condition2(c)){
            return Optional.of(f);
        } else {
            return Optional.empty();
        }
    }

    // TODO
    private boolean condition2(C c) {
        return false;
    }

    public Optional<Func<String>> interp(S s, Obj A){
        return null;
    }

}
