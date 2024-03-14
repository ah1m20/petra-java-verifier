package com.cognitionbox.petra.ast.interp;

import java.util.*;
import java.util.function.Predicate;

import com.cognitionbox.petra.ast.interp.util.loggers.ErrorLogger;
import com.cognitionbox.petra.ast.interp.util.loggers.ProofLogger;
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
import com.cognitionbox.petra.ast.terms.statements.c.CUnary;
import com.cognitionbox.petra.ast.terms.statements.c.CBinary;
import com.cognitionbox.petra.ast.terms.statements.s.*;
import com.google.common.collect.Sets;

import static com.cognitionbox.petra.ast.interp.util.Ops.*;

public final class Symbolic {
    final private ProofLogger PROOF_LOGGER = new ProofLogger();
    final private ErrorLogger ERROR_LOGGER = new ErrorLogger();
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
        PROOF_LOGGER.enter();
        Obj Aepsilon = lookupObj(prog.getAepsilon());
        Optional<Func<String>> m_epsilon = interpOverlineC(prog.getM(),lookupM(prog.getM(),Aepsilon),Aepsilon);
        if (
            !Aepsilon.isPrimitive() &&
            m_epsilon.isPresent()
//           && m_epsilon.get().dom().equals(Theta(Aepsilon))
//            && union(set(list(Aepsilon.getOverlinePhi(), phi->phi.getE()), e->interpE(e,Aepsilon))).equals(Omega(Aepsilon))
//            && (isReactive?PROOF_LOGGER.hasInitialState(Aepsilon,this):true)
        ){
            PROOF_LOGGER.exitWithNonBottom(prog,Aepsilon,"ENTRY");
            return m_epsilon;
        } else {
            PROOF_LOGGER.exitWithBottom(prog,Aepsilon,"ENTRY");
            return Optional.empty();
        }
    }

    Optional<Func<String>> interpProg(Prog prog){
        PROOF_LOGGER.enter();
        Obj Aepsilon = lookupObj(prog.getAepsilon());
        Optional<Func<String>> m_epsilon = interpOverlineC(prog.getM(),lookupM(prog.getM(),Aepsilon),Aepsilon);
        if (
                forall(prog.getObjs(), o->interpObj(o).isPresent()) &&
                forall(Aepsilon.getOverlineDelta(), delta->PROOF_LOGGER.exitWithBottom(delta,interpOverlineC(delta.getM(),lookupM(delta.getM(),Aepsilon), Aepsilon).isPresent(),Aepsilon,"RESOLVE"))
//            && interpObj(Aepsilon).isPresent() &&
//            m_epsilon.isPresent()
//           && m_epsilon.get().dom().equals(Theta(Aepsilon))
//            && union(set(list(Aepsilon.getOverlinePhi(), phi->phi.getE()), e->interpE(e,Aepsilon))).equals(Omega(Aepsilon))
//            && (isReactive?PROOF_LOGGER.hasInitialState(Aepsilon,this):true)
         ){
            PROOF_LOGGER.exitWithNonBottom(prog,Aepsilon,"ENTRY");
            return m_epsilon;
        } else {
            PROOF_LOGGER.exitWithBottom(prog,Aepsilon,"ENTRY");
            return Optional.empty();
        }
    }

    private final Map<Obj,Optional<IObj>> objCache = new HashMap<>();
    Optional<IObj> interpObj(Obj A){
        return objCache.computeIfAbsent(A,k->interpObjImpl(A));
    }

    Optional<IObj> interpObjImpl(Obj A){
        if (A.isPrimitive()){
            return interpPrimitiveObj(A);
        } else {
            return interpNonPrimitiveObj(A);
        }
    }

    //private Map<Obj,Set<String>> thetaCache = new HashMap<>();
    Set<String> Theta(Obj A){
        //return thetaCache.computeIfAbsent(A,k->set(set(A.getOverlinePhi()), (e->e.getP())));
        return set(set(A.getOverlinePhi()), (e->e.getP()));
    }

    Phi lookupPhi(String p, Obj A){
        Optional<Phi> phi = find(A.getOverlinePhi(), x->x.getP().equals(p));
        if (phi.isPresent()){
            return phi.get();
        } else {
            throw new IllegalArgumentException("cannot find predicate: "+p);
        }
    }

    Delta lookupDelta(String p, Obj A){
        Optional<Delta> delta = find(A.getOverlineDelta(), x->x.getM().equals(p));
        if (delta.isPresent()){
            return delta.get();
        } else {
            throw new IllegalStateException("cannot find method: "+p);
        }
    }

    E lookupE(String p, Obj A){
        return lookupPhi(p,A).getE();
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

    C lookupM(String m, Obj A){
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

    //private final Map<Obj,Set<List<String>>> omegaCache = new HashMap<>();
    Set<List<String>> Omega(Obj A){
        List<Set<String>> Thetas = list(A.getOverlineBeta(), beta->Theta(lookupObj(beta)) );
        //return omegaCache.computeIfAbsent(A,k->product(Thetas));
        return product(Thetas);
    }

    Optional<IObj> interpNonPrimitiveObj(Obj A){
        PROOF_LOGGER.enter();
        if (//forall(A.getOverlineBeta(), beta->PROOF_LOGGER.exitWithBottom(beta,interpObj(lookupObj(beta.getObjectId())).isPresent(),A,"OBJ")) &&
                forall(A.getOverlinePhi(), phi->isNotEmpty(phi.getP(),phi.getE(),A)) &&
                pairwiseDisjointE(list(A.getOverlinePhi(),phi->phi.getP()),list(A.getOverlinePhi(),phi->interpE(phi.getE(),A)), A)
                // && forall(A.getOverlineDelta(), delta->PROOF_LOGGER.exitWithBottom(delta,interpOverlineC(delta.getM(),lookupM(delta.getM(),A), A).isPresent(),A,"RESOLVE"))
//                && (isReactive?PROOF_LOGGER.isEqual(union(set(list(A.getOverlinePhi(), phi->phi.getE()), e->interpE(e,A))), Omega(A), A):true)
        ){
            //PROOF_LOGGER.exitWithNonBottom(A,A,"OBJ");
            return Optional.of(new IObj(Omega(A), interpOverlinePhi(A.getOverlinePhi(),A),interpDeltas(A.getOverlineDelta(),A)));
        } else {
            //logObjectPrivateStateSpace(Omega(A),A);
            //PROOF_LOGGER.exitWithBottom(A,A,"OBJ");
            return Optional.empty();
        }
    }

    public static <T> boolean forall(Collection<T> collection, Predicate<T> toHold){
        boolean res =  com.cognitionbox.petra.ast.interp.util.Ops.forall(collection,toHold);
        if (!res){
            //LOG.info("is not forall.");
            return false;
        } else {
            return true;
        }
    }

    public boolean isNotEmpty(String p, E e, Obj A){
        Set<List<String>> set = interpE(e, A);
        if (set.isEmpty()){
            ERROR_LOGGER.logPrivateStateSpace(p,set,A);
            return false;
        } else {
            return true;
        }
    }

    Optional<IObj> interpPrimitiveObj(Obj A){
        return Optional.of(new IObj(null, interpOverlinePhi(A.getOverlinePhi(),A),interpDeltas(A.getOverlineDelta(),A)));
    }

    Map<String, Set<List<String>>> interpOverlinePhi(List<Phi> overlinePhi, Obj A){
        Map<String, Set<List<String>>> record = new HashMap<>();
        if (A.isPrimitive()){
            overlinePhi.forEach(phi->record.put(phi.getP(),null));
        } else {
            overlinePhi.forEach(phi->record.put(phi.getP(), interpE(phi.getE(),A)));
        }
        return record;
    }

    Map<String, Optional<Func<String>>> interpDeltas(List<Delta> overlineDelta, Obj A){
        PROOF_LOGGER.enter();
        Map<String, Optional<Func<String>>> record = new HashMap<>();
        overlineDelta.forEach(delta->record.put(delta.getM(),interpOverlineC(delta.getM(),delta.getOverlineC(),A)));
        return record;
    }

    private final Map<C,Optional<Func<String>>> casesCache = new HashMap<>();
    Optional<Func<String>> interpOverlineC(String m, C ovelineC, Obj A){
        return casesCache.computeIfAbsent(ovelineC,k->interpOverlineCImpl(m,ovelineC,A));
    }

    Optional<Func<String>> interpOverlineCImpl(String m, C ovelineC, Obj A){
        PROOF_LOGGER.enter();
        if (ovelineC instanceof CBinary){
            Optional<Func<String>> left = interpOverlineC(m,((CBinary) ovelineC).getLeft(),A);
            Optional<Func<String>> right = interpOverlineC(m,((CBinary) ovelineC).getRight(),A);
            if (left.isPresent() && right.isPresent()){
                if (intersect(left.get().dom(),right.get().dom()).isEmpty()){
                    PROOF_LOGGER.exitWithNonBottom(lookupDelta(m,A),A,"CASES");
                    return Optional.of(functionUnion(left.get(),right.get()));
                } else {
                    PROOF_LOGGER.exitWithBottom(lookupDelta(m,A),A,"CASES");
                    ERROR_LOGGER.logCasesDomainOverlap(A.getFullyQualifiedClassName(),"CASES",m,((CBinary) ovelineC).getLeft().getId(),left.get().dom(),((CBinary) ovelineC).getRight().getId(),right.get().dom(),A);
                    return Optional.empty();
                }
            } else {
                PROOF_LOGGER.exitWithBottom(lookupDelta(m,A),A,"CASES");
                //ERROR_LOGGER.logEmptyCompositions(A.getFullyQualifiedClassName(),"CASES",m,left.get().dom(),right.get().dom());
                return Optional.empty();
            }
        } else if (ovelineC instanceof CUnary){
            return interpC(m, (CUnary) ovelineC,A);
        }
        throw new IllegalArgumentException();
    }

    boolean pairwiseDisjointE(List<String> theta, List<Set<List<String>>> interpEs, Obj A) {
        Set<List<String>> states = new HashSet<>();
        Set<String> thetasVisited = new HashSet<>();
        int expectedTotalSizeOfSets = 0;
        for (int i=0;i<theta.size();i++){
            thetasVisited.add(theta.get(i));
            Set<List<String>> interpE = interpEs.get(i);
            states.addAll(interpE);
            expectedTotalSizeOfSets += interpE.size();
            if (expectedTotalSizeOfSets!=states.size()){
                return pairwiseDisjointEwithExplanationIfFail(theta,interpEs,A); // if errors we can then explain them using the direct pairwise comparison
            }
        }
        return true;
    }

    boolean pairwiseDisjointEwithExplanationIfFail(List<String> theta, List<Set<List<String>>> interpEs, Obj A) {
        for (int i=0;i<theta.size();i++){
            for (int j=0;j<theta.size();j++){
                if (i!=j){
                    if (intersect(interpEs.get(i),interpEs.get(j)).size()!=0){
                        //logPrivateStateSpace(i.getP(),a,A);
                        //logPrivateStateSpace(j.getP(),b,A);
                        ERROR_LOGGER.logPredicatesOverlap(A.getFullyQualifiedClassName(),"OBJ",theta.get(i),interpEs.get(i),theta.get(j),interpEs.get(j),A);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    boolean pairwiseDisjointDomCwithExplanationIfFail(String m, List<Optional<Func<String>>> ovelineC, Obj A) {
        for (int i=0;i<ovelineC.size();i++){
            for (int j=0;j<ovelineC.size();j++){
                if (i!=j){
                    Optional<Func<String>> a = ovelineC.get(i);
                    Optional<Func<String>> b = ovelineC.get(j);
                    if (!a.isPresent() || !b.isPresent()){
                        return false;
                    }
                    if (intersect(a.get().dom(),b.get().dom()).size()!=0){
                        ERROR_LOGGER.logCasesDomainOverlap(A.getFullyQualifiedClassName(),"CASES",m,i,a.get().dom(),j,b.get().dom(),A);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    boolean pairwiseDisjointDomC(String m, List<Optional<Func<String>>> ovelineC, Obj A) {
        Set<String> thetas = new HashSet<>();
        int expectedTotalSizeOfSets = 0;
        for (int i=0;i<ovelineC.size();i++){
            Set<String> dom = ovelineC.get(i).get().dom();
            thetas.addAll(dom);
            expectedTotalSizeOfSets += dom.size();
            if (expectedTotalSizeOfSets!=thetas.size()){
                return pairwiseDisjointDomCwithExplanationIfFail(m,ovelineC,A); // if errors we can then explain them using the direct pairwise comparison
            }
        }
        return true;
    }

    private final Map<C,Optional<Func<String>>> caseCache = new HashMap<>();
    public Optional<Func<String>> interpC(String m, CUnary c, Obj A){
        return caseCache.computeIfAbsent(c, k->interpCImpl(m,c,A));
    }

    public Optional<Func<String>> interpCImpl(String m, CUnary c, Obj A){
        if (A.isPrimitive()){
            return interpPrimitiveC(c,A);
        } else {
            return interpNonPrimitiveC(m,c,A);
        }
    }

    Optional<Func<String>> interpNonPrimitiveC(String m, CUnary c, Obj A){
        //logStartProofTree();
        PROOF_LOGGER.enter();
        Optional<Func<List<String>>> s = interpS(c.getS(),A);
        if (condition1(s) &&
                condition2(m,c,A,s)){
            PROOF_LOGGER.exitWithNonBottom(c,A,"CASE");
            //logEndProofTree();
            Func<String> f = f(c,A,s);
            if (!f.def().isEmpty()){
                return Optional.of(f);
            } else {
                PROOF_LOGGER.exitWithBottom(c,A,"CASE");
                return Optional.empty();
            }
        } else {
            PROOF_LOGGER.exitWithBottom(c,A,"CASE");
            //logEndProofTree();
            return Optional.empty();
        }
    }

    Func<String> f(CUnary c, Obj A, Optional<Func<List<String>>> s){
        Set<String> P = interpPrePost(c.getPre(),A);
        Set<String> Q = interpPrePost(c.getPost(),A);
        Set<Map.Entry<String,String>> def = set();
        for (String p : P){
            int matches = 0;
            for (String p_ : Q){
                Set<List<String>> e_p = interpE(lookupE(p,A),A);
                Set<List<String>> e_p_ = interpE(lookupE(p_,A),A);
                if (s.isPresent()){
                    if (subseteq(s.get().image(e_p), e_p_)){
                        def.add(mapsto(p,p_));
                        matches++;
                    }
                }
            }
            if (matches>1){
                return func(P,Q,set());
            }
        }
        return func(P,Q,def);
    }

    Func<String> f(CUnary c, Obj A){
        Set<String> P = interpPrePost(c.getPre(),A);
        Set<String> Q = interpPrePost(c.getPost(),A);
        Set<Map.Entry<String,String>> def = set();
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

    Optional<Func<String>> interpPrimitiveC(CUnary c, Obj A){
        PROOF_LOGGER.enter();
        Set<String> pre = interpPrePost(c.getPre(),A);
        Set<String> post = interpPrePost(c.getPost(),A);
        if (post.size()>1){
            // base method not a function at its symbolic interpretation is constucted from the product pre x post
            PROOF_LOGGER.exitWithBottom(c,A,"BASECASE");
            return Optional.empty();
        }
        Set<Map.Entry<String,String>> def = set();
        for (String p : pre){
            for (String q : post){
                def.add(mapsto(p,q));
            }
        }
        PROOF_LOGGER.exitWithNonBottom(c,A,"BASECASE");
        return Optional.of(new Func<>(pre,post,def));
    }

    boolean condition1(Optional<Func<List<String>>> s) {
        return s.isPresent();
    }
    // TODO
    boolean condition2(String m, CUnary c, Obj A, Optional<Func<List<String>>> s) {
        Func<List<String>> interpS = s.get();
        Set<String> P = interpPrePost(c.getPre(),A);
        Set<String> Q = interpPrePost(c.getPost(),A);
        Set<E> e_p = set(P, p-> lookupE(p,A));
        Set<E> e_q = set(Q, q-> lookupE(q,A));
        Set<List<String>> in = union(set(e_p, e-> interpE(e,A)));
        Set<List<String>> out = union(set(e_q, e-> interpE(e,A)));
        if (!(subseteq(in,interpS.dom()) )){
            ERROR_LOGGER.preconditionisNotSubseteqDomain(A.getFullyQualifiedClassName(),"CASE",m,c.getId(),in,interpS.dom(),A);
            return false;
        }
        Set<List<String>> image = interpS.image(in);
        if (!subseteq(image, out)){
            ERROR_LOGGER.imageIsNotSubseteqPostcondition(A.getFullyQualifiedClassName(),"CASE",m,c.getId(),image,out,A);
            return false;
        }
        return true;
    }

    Func<List<String>> interpSkip(Skip skip, Obj A){
        return id(Omega(A));
    }

    //private final Map<Am,Optional<Func<List<String>>>> amCache = new HashMap<>();
    Optional<Func<List<String>>> interpAm(Am am, Obj A){
        PROOF_LOGGER.enter();
        Obj A_ = lookupObj(am.getA(),A);
        Optional<Func<String>> interp = interpOverlineC(am.getM(),lookupM(am.getM(),A_),A_);
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
            PROOF_LOGGER.exitWithNonBottom(am,A,"CALL");
            //return amCache.computeIfAbsent(am,k->Optional.of(functionProduct(funcs)));
            return Optional.of(functionProduct(funcs));
        } else {
            PROOF_LOGGER.exitWithBottom(am,A,"CALL");
            //return amCache.computeIfAbsent(am,k->Optional.empty());
            return Optional.empty();
        }
    }

    //private final Map<Ap,Set<List<String>>> apCache = new HashMap<>();
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
        //return apCache.computeIfAbsent(ap,k->product(listOfStates));
        return product(listOfStates);
    }

    //private final Map<EUnary,Set<List<String>>> eCache = new HashMap<>();
    Set<List<String>> interpE(EUnary unary, Obj A){
        if (unary.getOperator()==UnaryOperator.NOT){
            return Sets.difference(Omega(A),interpE(unary.getInner(),A));
            //return eCache.computeIfAbsent(unary,k->Sets.difference(Omega(A),interpE(unary.getInner(),A)));
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

    Set<String> interpPrePost(PrePost prePost, Obj A){
        return interpD((D) prePost, A);
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

    Set<String> fields(Z z){
        if (z instanceof Am){
            Am Am = ((Am) z);
            String a = Am.getA();
            return set(a);
        } else if (z instanceof ZBinary){
            ZBinary binary = ((ZBinary) z);
            Z right = binary.getRight();
            Z left = binary.getLeft();
            return union(fields(right),fields(left));
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
                    System.out.println("\t\tcase: "+interpOverlineC(d.getM(),d.getOverlineC(),o));
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
        PROOF_LOGGER.enter();
        Optional<Func<List<String>>> q = interpQ(binary.getLeft(), A);
        Optional<Func<List<String>>> qPrim = interpQ(binary.getRight(), A);
        if (q.isPresent() && qPrim.isPresent() && subseteq(q.get().range(), qPrim.get().dom())){
            Func<List<String>> f = qPrim.get().compose(q.get());
            PROOF_LOGGER.exitWithNonBottom(binary,A,"SEQ");
            return Optional.of(new Func<>(q.get().dom(), qPrim.get().range(),f.def()));
        } else {
            PROOF_LOGGER.exitWithBottom(binary,A,"SEQ");
            ERROR_LOGGER.preconditionisNotSubseteqDomain(A.getFullyQualifiedClassName(),"SEQ","",0,q.get().range(),qPrim.get().dom(),A);
            return Optional.empty();
        }
    }
    Optional<Func<List<String>>> interpZBinary(ZBinary binary, Obj A){
        PROOF_LOGGER.enter();
        Z z = binary.getLeft();
        Z zPrim = binary.getRight();
        Optional<Func<List<String>>> ir = interpZ(z, A);
        Optional<Func<List<String>>> irPrim = interpZ(zPrim, A);
        if (!(ir.isPresent() && irPrim.isPresent())){
            PROOF_LOGGER.exitWithBottom(binary,A,"PAR");
            return Optional.empty();
        }
        if (!intersect(fields(z), fields(zPrim)).isEmpty()){
            PROOF_LOGGER.exitWithBottom(binary,A,"PAR");
            return Optional.empty();
        }
        Set<List<String>> P = intersect(ir.get().dom(), irPrim.get().dom());
        Set<List<String>> Q = intersect(ir.get().range(), irPrim.get().range());
        Set<List<String>> V = ir.get().restrict(P).range();
        Func<List<String>> f = irPrim.get().restrict(V).compose(ir.get().restrict(P));
        PROOF_LOGGER.exitWithNonBottom(binary,A,"PAR");
        return Optional.of(new Func<>(P,Q,f.def()));
    }

    public boolean isInitialState(Phi phi, Obj A){
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
