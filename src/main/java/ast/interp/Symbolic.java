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

    private Obj lookup(String objectId){
        return objectTable.lookup(objectId);
    }
    private Obj objectDef(Beta beta){
        return objectTable.lookup(beta.getObjectId());
    }
    public Optional<IObj> interp(Obj A){
        List<Obj> A_i = list(A.getOverlineBeta(), beta -> lookup(beta.getObjectId()));
        List<E> e_j = list(A.getOverlinePhi(), phi->phi.getE());
        List<List<C>> overline_c_k = list(A.getOverlineDelta(), delta->delta.getOverlineC());
        if (forall(A_i, x->interp(x).isPresent()) &&
            forall(e_j, x-> !interp(x, A).isEmpty()) &&
            pairwiseDisjointE(e_j, A) &&
            forall(overline_c_k, x-> interp(x, A).isPresent())){
            List<Set<String>> Thetas = list(A.getOverlineBeta(), beta->Theta(objectDef(beta)) );
            Set<List<String>> Omega = Ops.product(Thetas);
            return Optional.of(new IObj(Omega,interp(A.getOverlinePhi()),interpDeltas(A.getOverlineDelta(),A)));
        } else {
            return Optional.empty();
        }
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

    private boolean pairwiseDisjointE(List<E> e_j, Obj A) {
        for (E i : e_j){
            for (E j : e_j){
                if (i!=j){
                    Set<List<String>> a = interp(i,A);
                    Set<List<String>> b = interp(j,A);
                    if (Sets.intersection(a,b).size()!=0){
                        return false;
                    }
                }
            }
        }
        return true;
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

    public Set<List<String>> interp(E e, Obj A){
        return null;
    }

}
