package com.cognitionbox.petra.examples.turingmachine2.objects;

import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.External;
import com.cognitionbox.petra.examples.turingmachine2.external.Tape;

@Base
public class HeadDir {
    @External
    private final Tape tape;

    public HeadDir(Tape tape) {
        this.tape = tape;
    }

    public boolean moveRightOnly() {
        return tape.moveRightOnly();
    }

    public boolean moveLeftOnly() {
        return tape.moveLeftOnly();
    }
}
