package ast.interp;

import ast.terms.*;
import ast.terms.expressions.e.E;
import ast.terms.statements.c.C;
import ast.terms.statements.s.S;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

public final class Symbolic {

    private final ObjectTable objectTable = new ObjectTable();
    private final OverlinePhiHelper overlinePhiHelper = new OverlinePhiHelper();
    private final OverlineDeltaHelper overlineDeltaHelper = new OverlineDeltaHelper();
    private final OverlineCHelper overlineCHelper = new OverlineCHelper();

    private final SHelper sHelper = new SHelper();

    public Symbolic(Prog prog){
        for (Obj obj : prog.getObjs()){
            objectTable.put(obj.getA(),obj);
        }
    }

    public Set<String> Theta(Obj A){
        return A.getOverlinePhi().stream().map(e->e.getP()).collect(Collectors.toSet());
    }
    public IObj interp(Obj A){
        List<Set<String>> Thetas = A.getOverlineBeta().stream().map(beta->Theta(objectTable.lookup(beta.getObjectId()))).collect(Collectors.toList());
        Set<List<String>> Omega = Ops.product(Thetas);
        return new IObj(Omega,overlinePhiHelper.interp(A.getOverlinePhi()),overlineDeltaHelper.interp(A.getOverlineDelta(),A));
    }

    private class OverlinePhiHelper {
        public Map<String, E> interp(List<Phi> overlinePhi){
            Map<String, E> record = new HashMap<>();
            for (Phi phi : overlinePhi){
                record.put(phi.getP(),phi.getE());
            }
            return record;
        }
    }

    private class OverlineDeltaHelper {
        public Map<String, Optional<Func<String>>> interp(List<Delta> overlineDelta, Obj A){
            Map<String, Optional<Func<String>>> record = new HashMap<>();
            for (Delta delta : overlineDelta){
                record.put(delta.getM(),overlineCHelper.interp(delta.getOverlineC(),A));
            }
            return record;
        }
    }

    private class OverlineCHelper {
        public Optional<Func<String>> interp(List<C> ovelineC, Obj A){
            if (ovelineC.stream().allMatch(c->interp(c,A).isPresent()) && pairwiseDisjoint(ovelineC,A)){
                return Optional.of(Ops.functionUnion(ovelineC.stream().map(c->interp(c,A).get()).collect(Collectors.toList())));
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
            if (sHelper.interp(c.getS(),A).isPresent() && condition2(c)){
                return Optional.of(f);
            } else {
                return Optional.empty();
            }
        }

        // TODO
        private boolean condition2(C c) {
            return false;
        }

    }

    private class SHelper {
        // TODO
        public Optional<Func<String>> interp(S s, Obj A){
            return null;
        }
    }

}
