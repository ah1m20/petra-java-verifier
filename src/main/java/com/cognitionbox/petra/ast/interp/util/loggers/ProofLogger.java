package com.cognitionbox.petra.ast.interp.util.loggers;

import com.cognitionbox.petra.ast.interp.Symbolic;
import com.cognitionbox.petra.ast.interp.util.DepthTracker;
import com.cognitionbox.petra.ast.terms.Obj;
import com.cognitionbox.petra.ast.terms.Term;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static com.cognitionbox.petra.ast.interp.util.Ops.existsOne;
import static com.cognitionbox.petra.ast.interp.util.Ops.intersect;

public final class ProofLogger {
    private final DepthTracker tracker = new DepthTracker();
    private final Logger LOG = new Logger();

    public <T> void enter() {
        tracker.markEntry();
    }

    public <T extends Term> void exitWithNonBottom(T t, String rule) {
        exitWithNonBottom(t,null,rule);
    }
    public <T extends Term> void exitWithBottom(T t, String rule) {
        exitWithBottom(t,null,rule);
    }

    public <T extends Term> void exitWithNonBottom(T t, Obj A, String rule){
        StringBuilder tabs = new StringBuilder();
        IntStream.range(0,tracker.depth()).forEach(x->tabs.append("|   "));
        LOG.debug(tabs+"["+t+"]"+(A!=null?"^{"+A+"}":"")+" != Bot"+" by "+rule);
        tracker.markExit();
    }

    public <T extends Term> void exitWithBottom(T t, Obj A, String rule){
        StringBuilder tabs = new StringBuilder();
        IntStream.range(0,tracker.depth()).forEach(x->tabs.append("|   "));
        LOG.debug(tabs+"["+t+"]"+(A!=null?"^{"+A+"}":"")+" = Bot"+" by "+rule);
        tracker.markExit();
    }

    public boolean isEqual(Object a, Object b, Obj A){
        if (!a.equals(b)){
            LOG.info(a+" is not equal to "+b);
            return false;
        } else {
            return true;
        }
    }

    public <T> void preconditionisNotSubseteqDomain(Set<List<String>> a, Set<List<String>> b, Obj A){
        LOG.info("\n"+" precondition: \n\n"+StatesLogging.toString(a)+"\n\nis not subset or equal to domain:\n\n"+StatesLogging.toString(b));
    }

    public <T> void preconditionisNotSubseteqDomain(String m, int caseId, Set<List<String>> a, Set<List<String>> b, Obj A){
        LOG.info("\n"+m+" c"+caseId+" precondition: \n\n"+StatesLogging.toString(a)+"\n\nis not subset or equal to domain:\n\n"+StatesLogging.toString(b));
    }

    public <T> void imageIsNotSubseteqPostcondition(String m, int caseId, Set<List<String>> a, Set<List<String>> b, Obj A){
        LOG.info("\n"+m+" c"+caseId+" image: \n\n"+StatesLogging.toString(a)+"\n\nis not subset or equal to postcondition:\n\n"+StatesLogging.toString(b));
    }

    public void logObjectPrivateStateSpace(Set<List<String>> set, Obj A){
        LOG.info("\n[Omega^{"+A.getA()+"}]^{"+A.getA()+"} = "+(!set.isEmpty()?StatesLogging.toString(set):"\\emptyset"));
    }

    public void logPrivateStateSpace(String label, Set<List<String>> set, Obj A){
        LOG.info("\n["+label+"]^{"+A.getA()+"} = "+(!set.isEmpty()?StatesLogging.toString(set):"\\emptyset"));
    }

    public void logCasesDomainOverlap(int i, Set<String> a, int j, Set<String> b, Obj A){
        LOG.info("\n[c_"+i+"]^{"+A.getA()+"} = \n"+StatesLogging.toString(a)+"\n\n overlaps with \n\n"+"[c_"+j+"]^{"+A.getA()+"} = \n"+StatesLogging.toString(b)+"\n\n on states \n\n"+StatesLogging.toString(intersect(a,b)));
    }

    public void logPredicatesOverlap(String p_i, Set<List<String>> a, String p_j, Set<List<String>> b, Obj A){
        LOG.info("\n["+p_i+"]^{"+A.getA()+"} = \n"+StatesLogging.toString(a)+"\n\n overlaps with \n\n"+"["+p_j+"]^{"+A.getA()+"} = \n"+StatesLogging.toString(b)+"\n\n on states \n\n"+StatesLogging.toString(intersect(a,b)));
    }

    public boolean hasInitialState(Obj A, Symbolic symbolic){
        boolean result = existsOne(A.getOverlinePhi(),phi->symbolic.isInitialState(phi,A));
        if (!result){
            LOG.info(A.getA()+" has no initial state.");
        }
        return result;
    }

    public <T extends Term> boolean exitWithBottom(T value, boolean holds, Obj A, String rule) {
        if (!holds) {
            exitWithBottom(value, A, rule);
            return false;
        }
        return true;
    }
}
