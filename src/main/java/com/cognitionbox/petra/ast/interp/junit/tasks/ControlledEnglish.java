package com.cognitionbox.petra.ast.interp.junit.tasks;

import com.cognitionbox.petra.ast.interp.PetraControlledEnglish;
import com.cognitionbox.petra.ast.interp.util.loggers.ControlledEnglishLogger;
import com.cognitionbox.petra.ast.terms.Obj;

import static com.cognitionbox.petra.ast.interp.PetraControlledEnglish.format;

public final class ControlledEnglish extends PetraTask {

    private final ControlledEnglishLogger logger;
    private Obj o;
    public ControlledEnglish(ControlledEnglishLogger logger, Obj o) {
        super(Integer.MAX_VALUE);
        this.logger = logger;
        this.o = o;
    }

    @Override
    public String toString() {
            return getClass().getSimpleName()+":"+o.getA();
        }

    @Override
    public Boolean call() throws Exception {
        logger.logControlledEnglishToFile(o.getFullyQualifiedClassName(),format(PetraControlledEnglish.translate(o),14));
        return true;
    }
}