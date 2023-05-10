package ast.interp;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import ast.terms.Obj;
import ast.terms.Prog;
import ast.terms.Beta;
import ast.terms.Phi;
import ast.terms.Delta;
import ast.terms.expressions.PrePost;
import ast.terms.expressions.d.D;
import ast.terms.expressions.d.DBinary;
import ast.terms.expressions.d.P;
import ast.terms.expressions.d.True;
import ast.terms.expressions.e.E;
import ast.terms.statements.c.C;
import ast.terms.statements.s.S;

import ast.interp.util.Ops;
import ast.interp.util.Set;
import static ast.interp.util.Collections.set;
import static ast.interp.util.Collections.list;
import static ast.interp.util.Collections.forall;
import static ast.interp.util.Collections.forEach;
import static ast.interp.util.Collections.find;
import static ast.interp.util.Ops.subseteq;

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

    private E lookup(String p, Obj A){
        Optional<Phi> phi = find(A.getOverlinePhi(), x->x.getP().equals(p));
        if (phi.isPresent()){
            return phi.get().getE();
        } else {
            throw new IllegalStateException("cannot find predicate label: "+p);
        }
    }
    private Obj lookup(String objectId){
        return objectTable.lookup(objectId);
    }
    private Obj objectDef(Beta beta){
        return objectTable.lookup(beta.getObjectId());
    }
    public Optional<IObj> interp(Obj A){
        Set<Obj> A_i = set(A.getOverlineBeta(), beta -> lookup(beta.getObjectId()));
        Set<E> e_j = set(A.getOverlinePhi(), phi->phi.getE());
        Set<List<C>> overline_c_k = set(A.getOverlineDelta(), delta->delta.getOverlineC());
        if (forall(A_i, x->interp(x).isPresent()) &&
            forall(e_j, x-> !interp(x, A).isEmpty()) &&
            pairwiseDisjointE(e_j, A) &&
            forall(overline_c_k, x-> interp(x, A).isPresent())){
            List<Set<String>> Thetas = list(A.getOverlineBeta().toList(), beta->Theta(objectDef(beta)) );
            Set<List<String>> Omega = Ops.product(Thetas);
            return Optional.of(new IObj(Omega,interp(A.getOverlinePhi()),interpDeltas(A.getOverlineDelta(),A)));
        } else {
            return Optional.empty();
        }
    }

    public Map<String, E> interp(Set<Phi> overlinePhi){
        Map<String, E> record = new HashMap<>();
        forEach(overlinePhi, phi->record.put(phi.getP(),phi.getE()));
        return record;
    }

    public Map<String, Optional<Func<String>>> interpDeltas(Set<Delta> overlineDelta, Obj A){
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

    private boolean pairwiseDisjointE(Set<E> e_j, Obj A) {
        for (E i : e_j){
            for (E j : e_j){
                if (i!=j){
                    Set<List<String>> a = interp(i,A);
                    Set<List<String>> b = interp(j,A);
                    if (Ops.intersect(a,b).size()!=0){
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
                    if (Ops.intersect(a.get().dom(),b.get().dom()).size()!=0){
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
        if (condition1(c,A) &&
                condition2(c,A)){
            return Optional.of(f);
        } else {
            return Optional.empty();
        }
    }

    private boolean condition1(C c, Obj A) {
        return interp(c.getS(),A).isPresent();
    }
    // TODO
    private boolean condition2(C c, Obj A) {
        Func<List<String>> interpS = interp(c.getS(),A).get();
        Set<String> P = interp(c.getPre(),A);
        Set<String> Q = interp(c.getPost(),A);
        Set<E> e_p = set(P, p->lookup(p,A));
        Set<E> e_q = set(Q, q->lookup(q,A));
        Set<List<String>> in = Ops.union(set(e_p, e->interp(e,A)));
        Set<List<String>> out = Ops.union(set(e_q, e->interp(e,A)));
        return subseteq(interpS.image(in), out);
    }

    public Optional<Func<List<String>>> interp(S s, Obj A){
        return null;
    }

    public Set<List<String>> interp(E e, Obj A){
        return null;
    }

    public Set<String> interp(PrePost prePost, Obj A){
        if (prePost instanceof True){
            return Theta(A);
        } else if (prePost instanceof D){
            return interp((D)prePost,A);
        }
        throw new IllegalArgumentException("prePost must be instanceof D or True.");
    }

    public Set<String> interp(D d, Obj A){
        if (d instanceof P){
            String p = ((P) d).getP();
            return set(p);
        } else if (d instanceof DBinary){
            DBinary binary = ((DBinary) d);
            D left = binary.getLeft();
            D right = binary.getRight();
            return Ops.union(interp(left,A),interp(right,A));
        }
        throw new IllegalArgumentException("d must be instanceof P or DBinary.");
    }

}
