package com.cognitionbox.petra.examples.turingmachine2.objects;

import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.External;
import com.cognitionbox.petra.examples.turingmachine2.external.Tape;

@Base
public class HeadVal {
    @External
    private final Tape tape;

    public HeadVal(Tape tape) {
        this.tape = tape;
    }
    // Moved view

    //@Override
    public boolean headTrue() {
        return tape.headTrue();
    }

    //@Override
    public boolean headFalse() {
        return tape.headFalse();
    }

    public boolean headUnkown() {
        return tape.headUnkown();
    }

    public void writeTrue(){
        if (headTrue() ^ headFalse() ^ headUnkown()){
            tape.writeTrue();
            assert(headTrue());
        }
    }

    public void writeFalse(){
        if (headTrue() ^ headFalse() ^ headUnkown()){
            tape.writeFalse();
            assert(headFalse());
        }
    }
}
