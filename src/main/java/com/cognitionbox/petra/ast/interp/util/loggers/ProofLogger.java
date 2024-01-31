package com.cognitionbox.petra.ast.interp.util.loggers;

import com.cognitionbox.petra.ast.interp.Symbolic;
import com.cognitionbox.petra.ast.interp.util.DepthTracker;
import com.cognitionbox.petra.ast.interp.util.Set;
import com.cognitionbox.petra.ast.terms.Obj;

import java.util.List;

import static com.cognitionbox.petra.ast.interp.util.Collections.existsOne;

public final class ProofLogger {
    private final SimpleProofLogger simpleProofLogger = new SimpleProofLogger();
    private final DepthTracker tracker = new DepthTracker();
    private final Logger LOG = new Logger();
    public <T> void logNonBottom(T t, String rule) {
        logNonBottom(t,null,rule);
    }
    public <T> void logBottom(T t, String rule) {
        logBottom(t,null,rule);
    }
    public <T> void logNonBottom(T t, Obj A, String rule) {
        simpleProofLogger.logNonBottom(LOG,tracker.depth(),t,A,rule);
        tracker.markExit();
    }
    public <T> void logBottom(T t, Obj A, String rule) {
        simpleProofLogger.logBottom(LOG,tracker.depth(),t,A,rule);
        tracker.markExit();
    }

    public <T> void log(T t, String rule) {
        tracker.markEntry();
    }

    public <T> void log(T t, Obj A, String rule) {
        tracker.markEntry();
    }

    public boolean isEqual(Object a, Object b, Obj A){
        if (!a.equals(b)){
            LOG.info(a+" is not equal to "+b);
            return false;
        } else {
            return true;
        }
    }

    public <T> void preconditionisNotSubseteqDomain(String m, int caseId, Set<List<String>> a, Set<List<String>> b, Obj A){
        //LOG.info(m+" c"+caseId+" precondition: \n\n"+a+"\n\nis not subset or equal to domain:\n\n"+b);
    }

    public <T> void imageIsNotSubseteqPostcondition(String m, int caseId, Set<List<String>> a, Set<List<String>> b, Obj A){
        //LOG.info(m+" c"+caseId+" image: \n\n"+a+"\n\nis not subset or equal to postcondition:\n\n"+b);
    }

    public void logObjectPrivateStateSpace(Set<List<String>> set, Obj A){
        LOG.info("[Omega^{"+A.getA()+"}]^{"+A.getA()+"} = "+(!set.isEmpty()?set:"\\emptyset"));
    }

    public void logPrivateStateSpace(String label, Set<List<String>> set, Obj A){
        //LOG.info("["+label+"]^{"+A.getA()+"} = "+(!set.isEmpty()?set:"\\emptyset"));
    }

    public void logCasesDomainOverlap(int i, Set<String> a, int j, Set<String> b, Obj A){
        //LOG.info("[c_"+i+"]^{"+A.getA()+"} = \n"+a+"\n\n overlaps with \n\n"+"[c_"+j+"]^{"+A.getA()+"} = "+b+"\n\n on states \n\n"+intersect(a,b));
    }

    public void logPredicatesOverlap(String p_i, Set<List<String>> a, String p_j, Set<List<String>> b, Obj A){
        //LOG.info("["+p_i+"]^{"+A.getA()+"} = \n"+a+"\n\n overlaps with \n\n"+"["+p_j+"]^{"+A.getA()+"} = "+b+"\n\n on states \n\n"+intersect(a,b));
    }

    public boolean hasInitialState(Obj A, Symbolic symbolic){
        boolean result = existsOne(A.getOverlinePhi(),phi->symbolic.isInitialState(phi,A));
        if (!result){
            LOG.info(A.getA()+" has no initial state.");
        }
        return result;
    }

    public <T> boolean logBottom(T value, boolean holds, Obj A, String rule) {
        if (!holds) {
            logBottom(value, A, rule);
            return false;
        }
        return true;
    }
}
