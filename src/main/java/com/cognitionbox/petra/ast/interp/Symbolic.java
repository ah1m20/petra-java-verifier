package com.cognitionbox.petra.ast.interp;

import java.util.*;
import java.util.function.Predicate;

import com.cognitionbox.petra.ast.interp.util.Logger;
import com.cognitionbox.petra.ast.terms.Obj;
import com.cognitionbox.petra.ast.terms.Prog;
import com.cognitionbox.petra.ast.terms.Beta;
import com.cognitionbox.petra.ast.terms.Phi;
import com.cognitionbox.petra.ast.terms.Delta;
import com.cognitionbox.petra.ast.terms.expressions.PrePost;
import com.cognitionbox.petra.ast.terms.expressions.d.D;
import com.cognitionbox.petra.ast.terms.expressions.d.DBinary;
import com.cognitionbox.petra.ast.terms.expressions.d.P;
import com.cognitionbox.petra.ast.terms.expressions.e.*;
import com.cognitionbox.petra.ast.terms.statements.c.C;
import com.cognitionbox.petra.ast.terms.statements.s.*;

import com.cognitionbox.petra.ast.interp.util.Set;
import com.cognitionbox.petra.ast.interp.util.Collections;
import com.cognitionbox.petra.ast.interp.util.Ops;
import com.cognitionbox.petra.ast.terms.expressions.e.*;
import com.cognitionbox.petra.ast.terms.statements.s.*;

public final class Symbolic {

    final static Logger LOG = new Logger();
    final ObjectTable objectTable = new ObjectTable();
    final boolean isReactive;
    Symbolic(Prog prog){
        this.isReactive = prog.isReactive();
        for (Obj obj : prog.getObjs()){
            if (obj.isValid()){
                objectTable.put(obj.getA(),obj);
            } else {
                throw new IllegalArgumentException("Cannot compute semantics due to invalid syntax.");
            }
        }
    }

    Optional<Func<String>> interpProgQuick(Prog prog){
        Obj Aepsilon = lookupObj(prog.getAepsilon());
        Optional<Func<String>> m_epsilon = interpOverlineC(lookupM(prog.getM(),Aepsilon),Aepsilon);
        if (
            !Aepsilon.isPrimitive() &&
            m_epsilon.isPresent() &&
            m_epsilon.get().dom().equals(Theta(Aepsilon))
            && Ops.union(com.cognitionbox.petra.ast.interp.util.Collections.set(com.cognitionbox.petra.ast.interp.util.Collections.list(Aepsilon.getOverlinePhi(), phi->phi.getE()), e->interpE(e,Aepsilon))).equals(Omega(Aepsilon))
            && (isReactive?hasInitialState(Aepsilon):true)
        ){
            return m_epsilon;
        } else {
            logBottom(prog);
            return Optional.empty();
        }
    }

    Optional<Func<String>> interpProg(Prog prog){
        Obj Aepsilon = lookupObj(prog.getAepsilon());
        Optional<Func<String>> m_epsilon = interpOverlineC(lookupM(prog.getM(),Aepsilon),Aepsilon);
        if (
                //forall(prog.getObjs(), o->interpObj(o).isPresent()) &&
            !Aepsilon.isPrimitive() &&
            interpObj(Aepsilon).isPresent() &&
            m_epsilon.isPresent() &&
            m_epsilon.get().dom().equals(Theta(Aepsilon))
            && Ops.union(com.cognitionbox.petra.ast.interp.util.Collections.set(com.cognitionbox.petra.ast.interp.util.Collections.list(Aepsilon.getOverlinePhi(), phi->phi.getE()), e->interpE(e,Aepsilon))).equals(Omega(Aepsilon))
            && (isReactive?hasInitialState(Aepsilon):true)
         ){
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

    com.cognitionbox.petra.ast.interp.util.Set<String> Theta(Obj A){
        return com.cognitionbox.petra.ast.interp.util.Collections.set(com.cognitionbox.petra.ast.interp.util.Collections.set(A.getOverlinePhi()), (e->e.getP()));
    }

    Phi lookupPhi(String p, Obj A){
        Optional<Phi> phi = com.cognitionbox.petra.ast.interp.util.Collections.find(A.getOverlinePhi(), x->x.getP().equals(p));
        if (phi.isPresent()){
            return phi.get();
        } else {
            throw new IllegalStateException("cannot find predicate: "+p);
        }
    }

    E lookupE(String p, Obj A){
        return lookupPhi(p,A).getE();
    }

    Obj lookupObj(String objectId){
        return objectTable.lookup(objectId);
    }

    Obj lookupObj(String a, Obj A){
        Optional<Beta> beta = com.cognitionbox.petra.ast.interp.util.Collections.find(A.getOverlineBeta(), x -> x.getFieldId().equals(a));
        if (beta.isPresent()){
            return lookupObj(beta.get().getObjectId());
        } else {
            throw new IllegalStateException("cannot find field: "+a);
        }
    }

    List<C> lookupM(String m, Obj A){
        Optional<Delta> delta = com.cognitionbox.petra.ast.interp.util.Collections.find(A.getOverlineDelta(), x -> x.getM().equals(m));
        if (delta.isPresent()){
            return delta.get().getOverlineC();
        } else {
            throw new IllegalStateException("cannot find method: "+m);
        }
    }

    Obj lookupObj(Beta beta){
        return objectTable.lookup(beta.getObjectId());
    }

    com.cognitionbox.petra.ast.interp.util.Set<List<String>> Omega(Obj A){
        List<com.cognitionbox.petra.ast.interp.util.Set<String>> Thetas = com.cognitionbox.petra.ast.interp.util.Collections.list(A.getOverlineBeta(), beta->Theta(lookupObj(beta)) );
        return Ops.product(Thetas);
    }

    Optional<IObj> interpNonPrimitiveObj(Obj A){
        if (forall(A.getOverlineBeta(), beta->logBottom(beta,interpObj(lookupObj(beta.getObjectId())).isPresent(),A)) &&
                forall(A.getOverlinePhi(), phi->isNotEmpty(phi.getP(),phi.getE(),A)) &&
                pairwiseDisjointE(A.getOverlinePhi(), A) &&
                forall(A.getOverlineDelta(), delta->logBottom(delta,interpOverlineC(lookupM(delta.getM(),A), A).isPresent(),A)) &&
                (isReactive?isEqual(Ops.union(com.cognitionbox.petra.ast.interp.util.Collections.set(com.cognitionbox.petra.ast.interp.util.Collections.list(A.getOverlinePhi(), phi->phi.getE()), e->interpE(e,A))), Omega(A), A):true)
        ){
            return Optional.of(new IObj(Omega(A), interpOverlinePhi(A.getOverlinePhi(),A),interpDeltas(A.getOverlineDelta(),A)));
        } else {
            //logObjectPrivateStateSpace(Omega(A),A);
            logBottom(A);
            return Optional.empty();
        }
    }
    public boolean isEqual(Object a, Object b, Obj A){
        if (!a.equals(b)){
            LOG.info(a+" is not equal to "+b);
            return false;
        } else {
            return true;
        }
    }

    public static <T> boolean forall(Collection<T> collection, Predicate<T> toHold){
        boolean res =  Collections.forall(collection,toHold);
        if (!res){
            //LOG.info("is not forall.");
            return false;
        } else {
            return true;
        }
    }


    public boolean isNotEmpty(String p, E e, Obj A){
        com.cognitionbox.petra.ast.interp.util.Set<List<String>> set = interpE(e, A);
        if (set.isEmpty()){
            logPrivateStateSpace(p,set,A);
            return false;
        } else {
            return true;
        }
    }

    public static <T> void preconditionisNotSubseteqDomain(com.cognitionbox.petra.ast.interp.util.Set<List<String>> a, com.cognitionbox.petra.ast.interp.util.Set<List<String>> b, Obj A){
        LOG.info("precondition: \n\n"+a+"\n\nis not subset or equal to domain:\n\n"+b);
    }

    public static <T> void imageIsNotSubseteqPostcondition(com.cognitionbox.petra.ast.interp.util.Set<List<String>> a, com.cognitionbox.petra.ast.interp.util.Set<List<String>> b, Obj A){
        LOG.info("image: \n\n"+a+"\n\nis not subset or equal to postcondition:\n\n"+b);
    }

    public static <T> boolean logBottom(T value, boolean holds, Obj A) {
        if (!holds) {
            logBottom(value, A);
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

    public static void logObjectPrivateStateSpace(com.cognitionbox.petra.ast.interp.util.Set<List<String>> set, Obj A){
        LOG.info("[Omega^{"+A.getA()+"}]^{"+A.getA()+"} = "+(!set.isEmpty()?set:"\\emptyset"));
    }

    public static void logPrivateStateSpace(String label, com.cognitionbox.petra.ast.interp.util.Set<List<String>> set, Obj A){
        LOG.info("["+label+"]^{"+A.getA()+"} = "+(!set.isEmpty()?set:"\\emptyset"));
    }

    public static void logCasesDomainOverlap(int i, com.cognitionbox.petra.ast.interp.util.Set<String> a, int j, com.cognitionbox.petra.ast.interp.util.Set<String> b, Obj A){
        LOG.info("[c_"+i+"]^{"+A.getA()+"} = "+a+"\n\n overlaps with \n\n"+"[c_"+j+"]^{"+A.getA()+"} = "+b+"\n\n on states \n\n"+ Ops.intersect(a,b));
    }

    public static void logPredicatesOverlap(String p_i, com.cognitionbox.petra.ast.interp.util.Set<List<String>> a, String p_j, com.cognitionbox.petra.ast.interp.util.Set<List<String>> b, Obj A){
        LOG.info("["+p_i+"]^{"+A.getA()+"} = "+a+"\n\n overlaps with \n\n"+"["+p_j+"]^{"+A.getA()+"} = "+b+"\n\n on states \n\n"+ Ops.intersect(a,b));
    }

    Optional<IObj> interpPrimitiveObj(Obj A){
        return Optional.of(new IObj(null, interpOverlinePhi(A.getOverlinePhi(),A),interpDeltas(A.getOverlineDelta(),A)));
    }

    Map<String, com.cognitionbox.petra.ast.interp.util.Set<List<String>>> interpOverlinePhi(List<Phi> overlinePhi, Obj A){
        Map<String, com.cognitionbox.petra.ast.interp.util.Set<List<String>>> record = new HashMap<>();
        if (A.isPrimitive()){
            com.cognitionbox.petra.ast.interp.util.Collections.forEach(overlinePhi, phi->record.put(phi.getP(),null));
        } else {
            com.cognitionbox.petra.ast.interp.util.Collections.forEach(overlinePhi, phi->record.put(phi.getP(), interpE(phi.getE(),A)));
        }
        return record;
    }

    Map<String, Optional<Func<String>>> interpDeltas(List<Delta> overlineDelta, Obj A){
        Map<String, Optional<Func<String>>> record = new HashMap<>();
        com.cognitionbox.petra.ast.interp.util.Collections.forEach(overlineDelta, delta->record.put(delta.getM(),interpOverlineC(delta.getOverlineC(),A)));
        return record;
    }

    Optional<Func<String>> interpOverlineC(List<C> ovelineC, Obj A){
        if (forall(ovelineC,c->logBottom(c,interpC(c,A).isPresent(),A)) && pairwiseDisjointDomC(ovelineC,A)){
            return Optional.of(Ops.functionUnion(com.cognitionbox.petra.ast.interp.util.Collections.list(ovelineC, c-> interpC(c,A).get())));
        } else {
            logBottom(ovelineC,A);
            return Optional.empty();
        }
    }

    boolean pairwiseDisjointE(List<Phi> phis, Obj A) {
        for (Phi i : phis){
            for (Phi j : phis){
                if (i!=j){
                    com.cognitionbox.petra.ast.interp.util.Set<List<String>> a = interpE(i.getE(),A);
                    com.cognitionbox.petra.ast.interp.util.Set<List<String>> b = interpE(j.getE(),A);
                    if (Ops.intersect(a,b).size()!=0){
                        logPrivateStateSpace(i.getP(),a,A);
                        logPrivateStateSpace(j.getP(),b,A);
                        logPredicatesOverlap(i.getP(),a,j.getP(),b,A);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    boolean pairwiseDisjointDomC(List<C> ovelineC, Obj A) {
        for (int i=0;i<ovelineC.size();i++){
            for (int j=0;j<ovelineC.size();j++){
                if (i!=j){
                    Optional<Func<String>> a = interpC(ovelineC.get(i),A);
                    Optional<Func<String>> b = interpC(ovelineC.get(j),A);
                    if (!a.isPresent() || !b.isPresent()){
                        return false;
                    }
                    if (Ops.intersect(a.get().dom(),b.get().dom()).size()!=0){
                        logCasesDomainOverlap(i,a.get().dom(),j,b.get().dom(),A);
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
        com.cognitionbox.petra.ast.interp.util.Set<String> P = interpPrePost(c.getPre(),A);
        com.cognitionbox.petra.ast.interp.util.Set<String> Q = interpPrePost(c.getPost(),A);
        com.cognitionbox.petra.ast.interp.util.Set<Mapsto<String,String>> def = com.cognitionbox.petra.ast.interp.util.Collections.set();
        for (String p : P){
            for (String p_ : Q){
                com.cognitionbox.petra.ast.interp.util.Set<List<String>> e_p = interpE(lookupE(p,A),A);
                com.cognitionbox.petra.ast.interp.util.Set<List<String>> e_p_ = interpE(lookupE(p_,A),A);
                Optional<Func<List<String>>> s = interpS(c.getS(),A);
                if (s.isPresent()){
                    if (Ops.subseteq(s.get().image(e_p), e_p_)){
                        def.add(Ops.mapsto(p,p_));
                    }
                }
            }
        }
        return new Func<>(P,Q,def);
    }

    Optional<Func<String>> interpPrimitiveC(C c, Obj A){
        com.cognitionbox.petra.ast.interp.util.Set<String> pre = interpPrePost(c.getPre(),A);
        com.cognitionbox.petra.ast.interp.util.Set<String> post = interpPrePost(c.getPost(),A);
        if (post.size()>1){
            // base method not a function at its symbolic interpretation is constucted from the product pre x post
            return Optional.empty();
        }
        com.cognitionbox.petra.ast.interp.util.Set<Mapsto<String,String>> def = com.cognitionbox.petra.ast.interp.util.Collections.set();
        for (String p : pre){
            for (String q : post){
                def.add(Ops.mapsto(p,q));
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
        com.cognitionbox.petra.ast.interp.util.Set<String> P = interpPrePost(c.getPre(),A);
        com.cognitionbox.petra.ast.interp.util.Set<String> Q = interpPrePost(c.getPost(),A);
        com.cognitionbox.petra.ast.interp.util.Set<E> e_p = com.cognitionbox.petra.ast.interp.util.Collections.set(P, p-> lookupE(p,A));
        com.cognitionbox.petra.ast.interp.util.Set<E> e_q = com.cognitionbox.petra.ast.interp.util.Collections.set(Q, q-> lookupE(q,A));
        com.cognitionbox.petra.ast.interp.util.Set<List<String>> in = Ops.union(com.cognitionbox.petra.ast.interp.util.Collections.set(e_p, e-> interpE(e,A)));
        com.cognitionbox.petra.ast.interp.util.Set<List<String>> out = Ops.union(com.cognitionbox.petra.ast.interp.util.Collections.set(e_q, e-> interpE(e,A)));
        if (!(Ops.subseteq(in,interpS.dom()) )){
            preconditionisNotSubseteqDomain(in,interpS.dom(),A);
            return false;
        }
        com.cognitionbox.petra.ast.interp.util.Set<List<String>> image = interpS.image(in);
        if (!Ops.subseteq(image, out)){
            imageIsNotSubseteqPostcondition(image,out,A);
            logBottom(c,A);
            return false;
        }
        return true;
    }

    Func<List<String>> interpSkip(Skip skip, Obj A){
        return Ops.id(Omega(A));
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
                    funcs.add(Ops.id(Theta(A_i)));
                }
            }
            return Optional.of(Ops.functionProduct(funcs));
        } else {
            logBottom(am,A);
            return Optional.empty();
        }
    }

    com.cognitionbox.petra.ast.interp.util.Set<List<String>> interpAp(Ap ap, Obj A){
        List<com.cognitionbox.petra.ast.interp.util.Set<String>> listOfStates = new ArrayList<>();
        for (int i=0;i<A.getOverlineBeta().size();i++){
            String fieldId = A.getOverlineBeta().get(i).getFieldId();
            String objectId = A.getOverlineBeta().get(i).getObjectId();
            Obj A_i = lookupObj(objectId);
            if (ap.getA().equals(fieldId)){
                listOfStates.add(com.cognitionbox.petra.ast.interp.util.Collections.singleton(ap.getP()));
            } else {
                listOfStates.add(Theta(A_i));
            }
        }
        return Ops.product(listOfStates);
    }
    com.cognitionbox.petra.ast.interp.util.Set<List<String>> interpE(EUnary unary, Obj A){
        if (unary.getOperator()== UnaryOperator.NOT){
            com.cognitionbox.petra.ast.interp.util.Set<List<String>> toRemove = interpE(unary.getInner(),A);
            com.cognitionbox.petra.ast.interp.util.Set<List<String>> result = Omega(A);
            result.removeAll(toRemove);
            return result;
        } else if (unary.getOperator()==UnaryOperator.PAREN){
            return interpE(unary.getInner(),A);
        }
        throw new IllegalArgumentException("operator must be NOT or PAREN");
    }

    com.cognitionbox.petra.ast.interp.util.Set<List<String>> interpE(EBinary binary, Obj A){
        if (binary.getOperator()==BinaryOperator.AND){
            return Ops.intersect(interpE(binary.getLeft(),A), interpE(binary.getRight(),A));
        } else if (binary.getOperator()==BinaryOperator.OR){
            return Ops.union(interpE(binary.getLeft(),A), interpE(binary.getRight(),A));
        }
        throw new IllegalArgumentException("operator must be AND or OR");
    }

    com.cognitionbox.petra.ast.interp.util.Set<List<String>> interpE(E e, Obj A){
        if (e instanceof True){
            return Omega(A);
        } else if (e instanceof Ap){
            return interpAp((Ap)e, A);
        } else if (e instanceof EUnary){
            return interpE((EUnary)e,A);
        } else if (e instanceof EBinary){
            return interpE((EBinary)e,A);
        }
        throw new IllegalArgumentException("must be instanceof Ap, EUnary or EBinary");
    }

    com.cognitionbox.petra.ast.interp.util.Set<String> interpPrePost(PrePost prePost, Obj A){
        return interpD((D) prePost, A);
    }

    com.cognitionbox.petra.ast.interp.util.Set<String> interpD(D d, Obj A){
        if (d instanceof P){
            String p = ((P) d).getP();
            return com.cognitionbox.petra.ast.interp.util.Collections.set(p);
        } else if (d instanceof DBinary){
            DBinary binary = ((DBinary) d);
            D left = binary.getLeft();
            D right = binary.getRight();
            return Ops.union(interpD(left,A), interpD(right,A));
        }
        throw new IllegalArgumentException("d must be instanceof P or DBinary.");
    }

    com.cognitionbox.petra.ast.interp.util.Set<String> fields(Z z){
        if (z instanceof Am){
            Am Am = ((Am) z);
            String a = Am.getA();
            return com.cognitionbox.petra.ast.interp.util.Collections.set(a);
        } else if (z instanceof ZBinary){
            ZBinary binary = ((ZBinary) z);
            Z right = binary.getRight();
            Z left = binary.getLeft();
            return Ops.union(fields(right),fields(left));
        }
        throw new IllegalArgumentException("z must be instanceof Am or ZBinary.");
    }

    public void printOutput() {
        for (Obj o : objectTable.values()){
            if (o instanceof Obj && !o.isPrimitive()){
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
                //pairwiseDisjointE(o.getOverlinePhi(),o);
                for (Delta d : o.getOverlineDelta()){
                    System.out.println("\t"+d.getM()+":");
                    for (C c : d.getOverlineC()){
                        System.out.println("\t\tcase: "+interpC(c,o));
                    }
                    //pairwiseDisjointDomC(d.getOverlineC(),o);
                }
            }
        }
    }

    Optional<Func<List<String>>> interpS(S s, Obj A){
        if (s instanceof Skip){
            return Optional.of(interpSkip((Skip)s, A));
        } else if (s instanceof Z){
            return interpZ((Z)s, A);
        }
        throw new IllegalArgumentException("s must be Skip, Am, RBinary or SBinary.");
    }

    Optional<Func<List<String>>> interpZ(Z z, Obj A){
        if (z instanceof ZBinary){
            return interpZBinary((ZBinary) z,A);
        } else if (z instanceof Q){
            return interpQ((Q) z,A);
        }
        throw new IllegalArgumentException("z must be a ZBinary or Q.");
    }

    Optional<Func<List<String>>> interpQ(Q q, Obj A){
        if (q instanceof QBinary){
            return interpQBinary((QBinary) q,A);
        } else if (q instanceof Am){
            return interpAm((Am)q, A);
        }
        throw new IllegalArgumentException("q must be a QBinary or Am.");
    }

    Optional<Func<List<String>>> interpQBinary(QBinary binary, Obj A){
        Q q = binary.getLeft();
        Q qPrim = binary.getRight();
        Optional<Func<List<String>>> ir = interpQ(q, A);
        Optional<Func<List<String>>> irPrim = interpQ(qPrim, A);
        if (ir.isPresent() && irPrim.isPresent() && Ops.subseteq(ir.get().range(), irPrim.get().dom())){
            Func<List<String>> f = irPrim.get().compose(ir.get());
            return Optional.of(new Func<>(ir.get().dom(), irPrim.get().range(),f.def()));
        } else {
            logBottom(binary,A);
            return Optional.empty();
        }
    }
    Optional<Func<List<String>>> interpZBinary(ZBinary binary, Obj A){
        Z z = binary.getLeft();
        Z zPrim = binary.getRight();
        Optional<Func<List<String>>> ir = interpZ(z, A);
        Optional<Func<List<String>>> irPrim = interpZ(zPrim, A);
        if (!(ir.isPresent() && irPrim.isPresent())){
            logBottom(binary,A);
            return Optional.empty();
        }
        if (!Ops.intersect(fields(z), fields(zPrim)).isEmpty()){
            logBottom(binary,A);
            return Optional.empty();
        }
        com.cognitionbox.petra.ast.interp.util.Set<List<String>> P = Ops.intersect(ir.get().dom(), irPrim.get().dom());
        com.cognitionbox.petra.ast.interp.util.Set<List<String>> Q = Ops.intersect(ir.get().range(), irPrim.get().range());
        Set<List<String>> V = ir.get().restrict(P).range();
        Func<List<String>> f = irPrim.get().restrict(V).compose(ir.get().restrict(P));
        return Optional.of(new Func<>(P,Q,f.def()));
    }

    boolean hasInitialState(Obj A){
        boolean result = Collections.existsOne(A.getOverlinePhi(), phi->isInitialState(phi,A));
        if (!result){
            LOG.info(A.getA()+" has no initial state.");
        }
        return result;
    }

    boolean isInitialState(Phi phi, Obj A){
        return phi.isInitial() && isInitialState(phi.getE(),A);
    }

    boolean isInitialState(EUnary unary, Obj A){
        if (unary.getOperator()== UnaryOperator.NOT){
            return !isInitialState(unary.getInner(),A);
        } else if (unary.getOperator()==UnaryOperator.PAREN){
            return isInitialState(unary.getInner(),A);
        }
        throw new IllegalArgumentException("operator must be NOT or PAREN");
    }

    boolean isInitialState(EBinary binary, Obj A){
        if (binary.getOperator()== BinaryOperator.AND){
            return isInitialState(binary.getLeft(),A) && isInitialState(binary.getRight(),A);
        } else if (binary.getOperator()==BinaryOperator.OR){
            return isInitialState(binary.getLeft(),A) || isInitialState(binary.getRight(),A);
        }
        throw new IllegalArgumentException("operator must be AND or OR");
    }

    boolean isInitialState(E e, Obj A){
        if (e instanceof Ap){
            Obj obj = lookupObj(((Ap)e).getA(),A);
            if (obj.isPrimitive()){
                return lookupPhi(((Ap)e).getP(),obj).isInitial();
            } else {
                E eP = lookupE(((Ap)e).getP(),obj);
                return isInitialState(eP,obj);
            }
        } else if (e instanceof EUnary){
            return isInitialState((EUnary)e,A);
        } else if (e instanceof EBinary){
            return isInitialState((EBinary)e,A);
        }
        throw new IllegalArgumentException("must be instanceof Ap, EUnary or EBinary");
    }
}
