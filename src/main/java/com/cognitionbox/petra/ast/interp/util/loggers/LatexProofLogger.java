package com.cognitionbox.petra.ast.interp.util.loggers;

import com.cognitionbox.petra.ast.terms.Obj;

import java.util.stream.IntStream;

public class LatexProofLogger {

    public <T> void logNonBottom(Logger logger, int depth, T t, String rule) {
        logNonBottom(logger,depth,t,null,rule);
    }
    public <T> void logBottom(Logger logger, int depth, T t, String rule) {
        logBottom(logger,depth,t,null,rule);
    }
    public <T> void logNonBottom(Logger logger, int depth, T t, Obj A, String rule) {
        StringBuilder tabs = new StringBuilder();
        IntStream.range(0,depth*2).forEach(x->tabs.append("\\quad"));
        logger.info(tabs+"\\overline{\\alpha\\llbracket "+t+" \\rrbracket"+(A!=null?"^{"+A+"}":"")+" \\neq \\bot}^{"+rule+"}\\\\&");
    }
    public <T> void logBottom(Logger logger, int depth, T t, Obj A, String rule) {
        StringBuilder tabs = new StringBuilder();
        IntStream.range(0,depth*2).forEach(x->tabs.append("\\quad"));
        logger.info(tabs+"\\overline{\\alpha\\llbracket "+t+" \\rrbracket"+(A!=null?"^{"+A+"}":"")+"} = \\bot}^{"+rule+"}\\\\&");
    }
}
