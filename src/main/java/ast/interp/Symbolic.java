package ast.interp;

import java.util.*;

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
import ast.terms.expressions.e.*;
import ast.terms.statements.c.C;
import ast.terms.statements.s.S;

import ast.interp.util.Ops;
import ast.interp.util.Set;
import ast.terms.statements.s.SBinary;
import ast.terms.statements.s.r.Am;
import ast.terms.statements.s.r.R;
import ast.terms.statements.s.r.RBinary;
import ast.terms.statements.s.r.Skip;

import static ast.interp.util.Collections.*;
import static ast.interp.util.Ops.*;

public final class Symbolic {
    private final ObjectTable objectTable = new ObjectTable();

    public Symbolic(Prog prog){
        for (Obj obj : prog.getObjs()){
            objectTable.put(obj.getA(),obj);
        }
    }

    public Set<String> Theta(Obj A){
        return set(set(A.getOverlinePhi()), (e->e.getP()));
    }

    private E lookup(String p, Obj A){
        Optional<Phi> phi = find(A.getOverlinePhi(), x->x.getP().equals(p));
        if (phi.isPresent()){
            return phi.get().getE();
        } else {
            throw new IllegalStateException("cannot find predicate: "+p);
        }
    }
    private Obj lookup(String objectId){
        return objectTable.lookup(objectId);
    }

    private List<C> lookupM(String m, Obj A){
        Optional<Delta> delta = find(A.getOverlineDelta(), x -> x.getM().equals(m));
        if (delta.isPresent()){
            return delta.get().getOverlineC();
        } else {
            throw new IllegalStateException("cannot find method: "+m);
        }
    }

    private Obj objectDef(Beta beta){
        return objectTable.lookup(beta.getObjectId());
    }

    private Set<List<String>> Omega(Obj A){
        List<Set<String>> Thetas = list(A.getOverlineBeta(), beta->Theta(objectDef(beta)) );
        return product(Thetas);
    }

    public Optional<IObj> interp(Obj A){
        List<Obj> A_i = list(A.getOverlineBeta(), beta -> lookup(beta.getObjectId()));
        List<E> e_j = list(A.getOverlinePhi(), phi->phi.getE());
        List<List<C>> overline_c_k = list(A.getOverlineDelta(), delta->delta.getOverlineC());
        if (forall(A_i, x->interp(x).isPresent()) &&
            forall(e_j, x-> !interp(x, A).isEmpty()) &&
            pairwiseDisjointE(e_j, A) &&
            forall(overline_c_k, x-> interp(x, A).isPresent())){
            Set<List<String>> Omega = Omega(A);
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
            return Optional.of(functionUnion(list(ovelineC, c->interp(c,A).get())));
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
                    if (intersect(a,b).size()!=0){
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
                    if (intersect(a.get().dom(),b.get().dom()).size()!=0){
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
        Set<List<String>> in = union(set(e_p, e->interp(e,A)));
        Set<List<String>> out = union(set(e_q, e->interp(e,A)));
        return subseteq(interpS.image(in), out);
    }

    public Optional<Func<List<String>>> interp(S s, Obj A){
        if (s instanceof Skip){
            return Optional.of(interp((Skip)s, A));
        } else if (s instanceof Am){
            return interp((Am)s, A);
        } else if (s instanceof RBinary){
            return interp((RBinary)s, A);
        } else if (s instanceof SBinary){
            return interp((SBinary)s,A);
        }
        throw new IllegalArgumentException("s must be Skip, Am, RBinary or SBinary.");
    }

    private Optional<Func<List<String>>> interp(SBinary binary, Obj A){
        Optional<Func<List<String>>> right = interp(binary.getRight(), A);
        Optional<Func<List<String>>> left = interp(binary.getLeft(), A);
        if (right.isPresent() && left.isPresent() &&
                subseteq(right.get().range(), left.get().dom()) ){
            return Optional.of(left.get().compose(right.get()));
        } else {
            return Optional.empty();
        }
    }

    private Func<List<String>> interp(Skip skip, Obj A){
        return id(Omega(A));
    }

    private Optional<Func<List<String>>> interp(Am am, Obj A){
        Optional<Func<String>> interp = interp(lookupM(am.getM(),A),A);
        if (interp.isPresent()){
            List<Func<String>> funcs = new ArrayList<>();
            for (int i=0;i<A.getOverlineBeta().size();i++){
                String objectId = A.getOverlineBeta().get(i).getObjectId();
                Obj A_i = lookup(objectId);
                if (am.getA().equals(objectId)){
                    funcs.add(interp.get());
                } else {
                    funcs.add(id(Theta(A_i)));
                }
            }
            return Optional.of(functionProduct(funcs));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Func<List<String>>> interp(R r, Obj A){
        if (r instanceof Am){
            return interp((Am)r, A);
        } else if (r instanceof RBinary){
            return interp((RBinary)r, A);
        }
        throw new IllegalArgumentException("operator must be Am or RBinary");
    }

    private Optional<Func<List<String>>> interp(RBinary binary, Obj A){
        R r = binary.getRight();
        R rPrim = binary.getLeft();
        Optional<Func<List<String>>> ir = interp(r, A);
        Optional<Func<List<String>>> irPrim = interp(rPrim, A);
        if (ir.isPresent() && irPrim.isPresent() &&
                !intersect(fields(r), fields(rPrim)).isEmpty() ){
            Set<List<String>> P = intersect(ir.get().dom(), irPrim.get().dom());
            Set<List<String>> Q = intersect(ir.get().range(), irPrim.get().range());
            Set<List<String>> V = irPrim.get().restrict(P).range();
            return Optional.of(irPrim.get().restrict(V).compose(ir.get().restrict(P)));
        } else {
            return Optional.empty();
        }
    }

    private Set<List<String>> interp(Ap ap, Obj A){
        List<Set<String>> listOfStates = new ArrayList<>();
        for (int i=0;i<A.getOverlineBeta().size();i++){
            String objectId = A.getOverlineBeta().get(i).getObjectId();
            Obj A_i = lookup(objectId);
            if (ap.getA().equals(objectId)){
                listOfStates.add(singleton(ap.getP()));
            } else {
                listOfStates.add(Theta(A_i));
            }
        }
        return product(listOfStates);
    }
    private Set<List<String>> interp(EUnary unary, Obj A){
        if (unary.getOperator()==UnaryOperator.NOT){
            Set<List<String>> toRemove = interp(unary.getInner(),A);
            Set<List<String>> result = Omega(A);
            result.removeAll(toRemove);
            return result;
        } else if (unary.getOperator()==UnaryOperator.PAREN){
            return interp(unary.getInner(),A);
        }
        throw new IllegalArgumentException("operator must be NOT or PAREN");
    }

    private Set<List<String>> interp(EBinary binary, Obj A){
        if (binary.getOperator()==BinaryOperator.AND){
            return intersect(interp(binary.getLeft(),A),interp(binary.getLeft(),A));
        } else if (binary.getOperator()==BinaryOperator.OR){
            return union(interp(binary.getLeft(),A),interp(binary.getLeft(),A));
        }
        throw new IllegalArgumentException("operator must be AND or AND");
    }

    public Set<List<String>> interp(E e, Obj A){
        if (e instanceof Ap){
            return interp((Ap)e, A);
        } else if (e instanceof EUnary){
            return interp((EUnary)e,A);
        } else if (e instanceof EBinary){
            return interp((EBinary)e,A);
        }
        throw new IllegalArgumentException("must be instanceof Ap, EUnary or EBinary");
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
            return union(interp(left,A),interp(right,A));
        }
        throw new IllegalArgumentException("d must be instanceof P or DBinary.");
    }

    private Set<String> fields(R r){
        if (r instanceof Am){
            Am Am = ((Am) r);
            String a = Am.getA();
            return set(a);
        } else if (r instanceof RBinary){
            RBinary binary = ((RBinary) r);
            R right = binary.getRight();
            R left = binary.getLeft();
            return union(fields(right),fields(left));
        }
        throw new IllegalArgumentException("r must be instanceof Am or RBinary.");
    }

}
