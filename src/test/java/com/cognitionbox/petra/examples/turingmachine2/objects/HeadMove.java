package com.cognitionbox.petra.examples.turingmachine2.objects;

import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.External;
import com.cognitionbox.petra.examples.turingmachine2.external.Tape;

@Base
public class HeadMove {
    @External
    private final Tape tape;

    public HeadMove(Tape tape) {
        this.tape = tape;
    }

    // Moved view
    public boolean movedLeft() {
        return tape.movedLeft();
    }

    //@Override
    public boolean movedRight() {
        return tape.movedRight();
    }

    //@Override
    public boolean movedNot() {
        return tape.notMoved();
    }

    public void moveLeft(){
        if (movedLeft() ^ movedRight() ^ movedNot()){
            tape.moveLeft();
            assert(movedLeft());
        }
    }

    public void moveRight(){
        if (movedLeft() ^ movedRight() ^ movedNot()){
            tape.moveRight();
            assert(movedRight());
        }
    }

    public void moveNot(){
        if (movedLeft() ^ movedRight() ^ movedNot()){
            tape.moveNot();
            assert(movedNot());
        }
    }
}
