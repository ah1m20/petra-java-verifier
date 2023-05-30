package ast.interp;

import java.util.*;

import ast.interp.util.Logger;
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

import ast.interp.util.Set;
import ast.terms.statements.s.SBinary;
import ast.terms.statements.s.r.Am;
import ast.terms.statements.s.r.R;
import ast.terms.statements.s.r.RBinary;
import ast.terms.statements.s.r.Skip;

import static ast.interp.util.Collections.*;
import static ast.interp.util.Ops.*;

public final class Symbolic {

    final static Logger LOG = new Logger();
    final ObjectTable objectTable = new ObjectTable();

    Symbolic(Prog prog){
        for (Obj obj : prog.getObjs()){
            objectTable.put(obj.getA(),obj);
        }
    }

    Optional<Func<String>> interpProg(Prog prog){
        Obj Aepsilon = lookupObj(prog.getAepsilon());
        Optional<Func<String>> m_epsilon = interpOverlineC(lookupM(prog.getM(),Aepsilon),Aepsilon);
        if (!Aepsilon.isPrimitive() &&
            interpObj(Aepsilon).isPresent() &&
                m_epsilon.isPresent() &&
                m_epsilon.get().dom().equals(Theta(Aepsilon)) &&
                union(set(list(Aepsilon.getOverlinePhi(), phi->phi.getE()), e->interpE(e,Aepsilon))).equals(Omega(Aepsilon))) {
            return m_epsilon;
        } else {
            logBottom(prog);
            return Optional.empty();
        }
    }

    Optional<IObj> interpObj(Obj A){
        if (A.isPrimitive()){
            return interpPrimitiveObj(A);
        } else {
            return interpNonPrimitiveObj(A);
        }
    }

    Set<String> Theta(Obj A){
        return set(set(A.getOverlinePhi()), (e->e.getP()));
    }

    E lookupE(String p, Obj A){
        Optional<Phi> phi = find(A.getOverlinePhi(), x->x.getP().equals(p));
        if (phi.isPresent()){
            return phi.get().getE();
        } else {
            throw new IllegalStateException("cannot find predicate: "+p);
        }
    }
    Obj lookupObj(String objectId){
        return objectTable.lookup(objectId);
    }

    Obj lookupObj(String a, Obj A){
        Optional<Beta> beta = find(A.getOverlineBeta(), x -> x.getFieldId().equals(a));
        if (beta.isPresent()){
            return lookupObj(beta.get().getObjectId());
        } else {
            throw new IllegalStateException("cannot find field: "+a);
        }
    }

    List<C> lookupM(String m, Obj A){
        Optional<Delta> delta = find(A.getOverlineDelta(), x -> x.getM().equals(m));
        if (delta.isPresent()){
            return delta.get().getOverlineC();
        } else {
            throw new IllegalStateException("cannot find method: "+m);
        }
    }

    Obj lookupObj(Beta beta){
        return objectTable.lookup(beta.getObjectId());
    }

    Set<List<String>> Omega(Obj A){
        List<Set<String>> Thetas = list(A.getOverlineBeta(), beta->Theta(lookupObj(beta)) );
        return product(Thetas);
    }

    Optional<IObj> interpNonPrimitiveObj(Obj A){
        List<Obj> A_i = list(A.getOverlineBeta(), beta -> lookupObj(beta.getObjectId()));
        List<E> e_j = list(A.getOverlinePhi(), phi->phi.getE());
        List<String> overline_m_k = list(A.getOverlineDelta(), delta->delta.getM());
        if (forall(A_i, x->logBottom(x,interpObj(x).isPresent(),A)) &&
                forall(e_j, x->logEmptySet(x,!interpE(x, A).isEmpty(),A)) &&
                pairwiseDisjointE(e_j, A) &&
                forall(overline_m_k, x->logBottom(x,interpOverlineC(lookupM(x,A), A).isPresent(),A)) &&
                union(set(list(A.getOverlinePhi(), phi->phi.getE()), e->interpE(e,A))).equals(Omega(A))){
            Set<List<String>> Omega = Omega(A);
            return Optional.of(new IObj(Omega, interpOverlinePhi(A.getOverlinePhi(),A),interpDeltas(A.getOverlineDelta(),A)));
        } else {
            logBottom(A);
            return Optional.empty();
        }
    }

    public static <T> boolean logBottom(T value, boolean holds, Obj A){
        if (!holds){
            logBottom(value,A);
            return false;
        }
        return true;
    }

    public static <T> boolean logEmptySet(T value, boolean holds, Obj A){
        if (!holds){
            logEmptySet(value,A);
            return false;
        }
        return true;
    }

    public static <T> void logBottom(T t){
        LOG.info("["+t+"] = \\bot");
    }

    public static <T> void logBottom(T t, Obj A){
        LOG.info("["+t+"]^{"+A.getA()+"} = \\bot");
    }

    public static <T> void logEmptySet(T t, Obj A){
        LOG.info("["+t+"]^{"+A.getA()+"} = \\emptyset");
    }

    public static <T> void logOverlapE(int i, Set<List<String>> a, int j, Set<List<String>> b, Obj A){
        LOG.info("predicates "+i+" overlaps with "+j+" on states: "+intersect(a,b)+" in "+A.getA());
        LOG.info("["+i+"]^{"+A.getA()+"} = "+a+" overlaps with "+"["+j+"]^{"+A.getA()+"} = "+b);
    }

    public static <T> void logOverlapC(C i, Set<String> a, C j, Set<String> b, Obj A){
        LOG.info("["+i+"]^{"+A.getA()+"} = "+a+" overlaps with "+"["+j+"]^{"+A.getA()+"} = "+b);
    }

    Optional<IObj> interpPrimitiveObj(Obj A){
        return Optional.of(new IObj(null, interpOverlinePhi(A.getOverlinePhi(),A),interpDeltas(A.getOverlineDelta(),A)));
    }

    Map<String, Set<List<String>>> interpOverlinePhi(List<Phi> overlinePhi, Obj A){
        Map<String, Set<List<String>>> record = new HashMap<>();
        if (A.isPrimitive()){
            forEach(overlinePhi, phi->record.put(phi.getP(),null));
        } else {
            forEach(overlinePhi, phi->record.put(phi.getP(), interpE(phi.getE(),A)));
        }
        return record;
    }

    Map<String, Optional<Func<String>>> interpDeltas(List<Delta> overlineDelta, Obj A){
        Map<String, Optional<Func<String>>> record = new HashMap<>();
        forEach(overlineDelta, delta->record.put(delta.getM(),interpOverlineC(delta.getOverlineC(),A)));
        return record;
    }

    Optional<Func<String>> interpOverlineC(List<C> ovelineC, Obj A){
        if (forall(ovelineC,c->logBottom(c,interpC(c,A).isPresent(),A)) && pairwiseDisjointC(ovelineC,A)){
            return Optional.of(functionUnion(list(ovelineC, c-> interpC(c,A).get())));
        } else {
            logBottom(ovelineC,A);
            return Optional.empty();
        }
    }

    boolean pairwiseDisjointE(List<E> e_j, Obj A) {
        int x = 0;
        for (E i : e_j){
            int y = 0;
            for (E j : e_j){
                if (i!=j){
                    Set<List<String>> a = interpE(i,A);
                    Set<List<String>> b = interpE(j,A);
                    if (intersect(a,b).size()!=0){
                        logOverlapE(x,a,y,b,A);
                        return false;
                    }
                }
                y++;
            }
            x++;
        }
        return true;
    }

    boolean pairwiseDisjointC(List<C> ovelineC, Obj A) {
        for (C i : ovelineC){
            for (C j : ovelineC){
                if (i!=j){
                    Optional<Func<String>> a = interpC(i,A);
                    Optional<Func<String>> b = interpC(j,A);
                    if (intersect(a.get().dom(),b.get().dom()).size()!=0){
                        logOverlapC(i,a.get().dom(),j,b.get().dom(),A);
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public Optional<Func<String>> interpC(C c, Obj A){
        if (A.isPrimitive() || c.getS() instanceof Skip){
            return interpPrimitiveC(c,A);
        } else {
            return interpNonPrimitiveC(c,A);
        }
    }

    Optional<Func<String>> interpNonPrimitiveC(C c, Obj A){
        if (condition1(c,A) &&
                condition2(c,A)){
            return Optional.of(f(c,A));
        } else {
            logBottom(c,A);
            return Optional.empty();
        }
    }

    Func<String> f(C c, Obj A){
        Set<String> P = interpPrePost(c.getPre(),A);
        Set<String> Q = interpPrePost(c.getPost(),A);
        Set<Mapsto<String,String>> def = set();
        for (String p : P){
            for (String p_ : Q){
                Set<List<String>> e_p = interpE(lookupE(p,A),A);
                Set<List<String>> e_p_ = interpE(lookupE(p_,A),A);
                Optional<Func<List<String>>> s = interpS(c.getS(),A);
                if (s.isPresent()){
                    if (subseteq(s.get().image(e_p), e_p_)){
                        def.add(mapsto(p,p_));
                    }
                }
            }
        }
        return new Func<>(P,Q,def);
    }

    Optional<Func<String>> interpPrimitiveC(C c, Obj A){
        Set<String> pre = interpPrePost(c.getPre(),A);
        Set<String> post = interpPrePost(c.getPost(),A);
        Set<Mapsto<String,String>> def = set();
        for (String p : pre){
            for (String q : post){
                def.add(mapsto(p,q));
            }
        }
        return Optional.of(new Func<>(pre,post,def));
    }

    boolean condition1(C c, Obj A) {
        boolean result = interpS(c.getS(),A).isPresent();
        if (!result){
            logBottom(c,A);
        }
        return result;
    }
    // TODO
    boolean condition2(C c, Obj A) {
        Func<List<String>> interpS = interpS(c.getS(),A).get();
        Set<String> P = interpPrePost(c.getPre(),A);
        Set<String> Q = interpPrePost(c.getPost(),A);
        Set<E> e_p = set(P, p-> lookupE(p,A));
        Set<E> e_q = set(Q, q-> lookupE(q,A));
        Set<List<String>> in = union(set(e_p, e-> interpE(e,A)));
        Set<List<String>> out = union(set(e_q, e-> interpE(e,A)));
        boolean result = subseteq(interpS.image(in), out);
        if (!result){
            logBottom(c,A);
        }
        return result;
    }

    Optional<Func<List<String>>> interpS(S s, Obj A){
        if (s instanceof Skip){
            return Optional.of(interpSkip((Skip)s, A));
        } else if (s instanceof Am){
            return interpAm((Am)s, A);
        } else if (s instanceof RBinary){
            return interpR((RBinary)s, A);
        } else if (s instanceof SBinary){
            return interpS((SBinary)s,A);
        }
        throw new IllegalArgumentException("s must be Skip, Am, RBinary or SBinary.");
    }

    Optional<Func<List<String>>> interpS(SBinary binary, Obj A){
        Optional<Func<List<String>>> ir = interpS(binary.getLeft(), A);
        Optional<Func<List<String>>> irPrim = interpS(binary.getRight(), A);

        if (!(ir.isPresent() && irPrim.isPresent())){
            logBottom(binary,A);
            return Optional.empty();
        }
        Set<List<String>> P = intersect(ir.get().dom(), irPrim.get().dom());
        Set<List<String>> Q = intersect(ir.get().range(), irPrim.get().range());
        Set<List<String>> V = ir.get().restrict(P).range();

        if ( subseteq(ir.get().restrict(P).range(), irPrim.get().dom()) ){
            Func<List<String>> f = irPrim.get().restrict(V).compose(ir.get().restrict(P));
            return Optional.of(new Func<>(P,Q,f.def()));
        } else {
            logBottom(binary,A);
            return Optional.empty();
        }
    }

    Func<List<String>> interpSkip(Skip skip, Obj A){
        return id(Omega(A));
    }

    Optional<Func<List<String>>> interpAm(Am am, Obj A){
        Obj A_ = lookupObj(am.getA(),A);
        Optional<Func<String>> interp = interpOverlineC(lookupM(am.getM(),A_),A_);
        if (interp.isPresent()){
            List<Func<String>> funcs = new ArrayList<>();
            for (int i=0;i<A.getOverlineBeta().size();i++){
                String objectId = A.getOverlineBeta().get(i).getObjectId();
                String fieldId = A.getOverlineBeta().get(i).getFieldId();
                Obj A_i = lookupObj(objectId);
                if (am.getA().equals(fieldId)){
                    funcs.add(interp.get());
                } else {
                    funcs.add(id(Theta(A_i)));
                }
            }
            return Optional.of(relationProduct(funcs));
        } else {
            logBottom(am,A);
            return Optional.empty();
        }
    }

    Optional<Func<List<String>>> interpR(R r, Obj A){
        if (r instanceof Am){
            return interpAm((Am)r, A);
        } else if (r instanceof RBinary){
            return interpR((RBinary)r, A);
        }
        throw new IllegalArgumentException("operator must be Am or RBinary");
    }

    Optional<Func<List<String>>> interpR(RBinary binary, Obj A){
        R r = binary.getLeft();
        R rPrim = binary.getRight();
        Optional<Func<List<String>>> ir = interpR(r, A);
        Optional<Func<List<String>>> irPrim = interpR(rPrim, A);
        if (ir.isPresent() && irPrim.isPresent() &&
                intersect(fields(r), fields(rPrim)).isEmpty() ){
            Set<List<String>> P = intersect(ir.get().dom(), irPrim.get().dom());
            Set<List<String>> Q = intersect(ir.get().range(), irPrim.get().range());
            Set<List<String>> V = ir.get().restrict(P).range();
            Func<List<String>> f = irPrim.get().restrict(V).compose(ir.get().restrict(P));
            return Optional.of(new Func<>(P,Q,f.def()));
        } else {
            logBottom(binary,A);
            return Optional.empty();
        }
    }

    Set<List<String>> interpAp(Ap ap, Obj A){
        List<Set<String>> listOfStates = new ArrayList<>();
        for (int i=0;i<A.getOverlineBeta().size();i++){
            String fieldId = A.getOverlineBeta().get(i).getFieldId();
            String objectId = A.getOverlineBeta().get(i).getObjectId();
            Obj A_i = lookupObj(objectId);
            if (ap.getA().equals(fieldId)){
                listOfStates.add(singleton(ap.getP()));
            } else {
                listOfStates.add(Theta(A_i));
            }
        }
        return product(listOfStates);
    }
    Set<List<String>> interpE(EUnary unary, Obj A){
        if (unary.getOperator()==UnaryOperator.NOT){
            Set<List<String>> toRemove = interpE(unary.getInner(),A);
            Set<List<String>> result = Omega(A);
            result.removeAll(toRemove);
            return result;
        } else if (unary.getOperator()==UnaryOperator.PAREN){
            return interpE(unary.getInner(),A);
        }
        throw new IllegalArgumentException("operator must be NOT or PAREN");
    }

    Set<List<String>> interpE(EBinary binary, Obj A){
        if (binary.getOperator()==BinaryOperator.AND){
            return intersect(interpE(binary.getLeft(),A), interpE(binary.getRight(),A));
        } else if (binary.getOperator()==BinaryOperator.OR){
            return union(interpE(binary.getLeft(),A), interpE(binary.getRight(),A));
        }
        throw new IllegalArgumentException("operator must be AND or OR");
    }

    Set<List<String>> interpE(E e, Obj A){
        if (e instanceof Ap){
            return interpAp((Ap)e, A);
        } else if (e instanceof EUnary){
            return interpE((EUnary)e,A);
        } else if (e instanceof EBinary){
            return interpE((EBinary)e,A);
        }
        throw new IllegalArgumentException("must be instanceof Ap, EUnary or EBinary");
    }

    Set<String> interpPrePost(PrePost prePost, Obj A){
        if (prePost instanceof True){
            return Theta(A);
        } else if (prePost instanceof D){
            return interpD((D)prePost,A);
        }
        throw new IllegalArgumentException("prePost must be instanceof D or True.");
    }

    Set<String> interpD(D d, Obj A){
        if (d instanceof P){
            String p = ((P) d).getP();
            return set(p);
        } else if (d instanceof DBinary){
            DBinary binary = ((DBinary) d);
            D left = binary.getLeft();
            D right = binary.getRight();
            return union(interpD(left,A), interpD(right,A));
        }
        throw new IllegalArgumentException("d must be instanceof P or DBinary.");
    }

    Set<String> fields(R r){
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
        throw new IllegalArgumentException("s must be instanceof Am or Binary.");
    }

    public void printOutput() {
        for (Obj o : objectTable.values()){
            if (o instanceof Obj){
                System.out.println(o.getA()+": "+interpObj(o));
                System.out.println("\t\\Omega = "+Omega(o));
                System.out.println("\t\\Theta = "+Theta(o));
                for (Phi p : o.getOverlinePhi()){
                    if (!o.isPrimitive()){
                        System.out.println("\t"+p.getP()+" = "+interpE(lookupE(p.getP(),o),o));
                    } else {
                        System.out.println("\t"+p.getP());
                    }
                }
                pairwiseDisjointE(list(o.getOverlinePhi(),phi->phi.getE()),o);
                for (Delta d : o.getOverlineDelta()){
                    System.out.println("\t"+d.getM()+":");
                    for (C c : d.getOverlineC()){
                        System.out.println("\t\tcase: "+interpC(c,o));
                    }
                    if (forall(d.getOverlineC(),c->interpC(c,o).isPresent())){
                        pairwiseDisjointC(d.getOverlineC(),o);
                    }
                }
            }
        }
    }
}
