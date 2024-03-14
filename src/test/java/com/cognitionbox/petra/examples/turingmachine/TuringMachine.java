package com.cognitionbox.petra.examples.turingmachine;

/*
 * Spec:
 * [0,0,0,0,0] State A
 * [1,0,0,0,0] State A
 * [1,1,0,0,0] State A
 * [1,1,1,0,0] State A
 * [1,1,1,1,0] State A
 * [1,1,1,1,1] State A
 * [1,1,1,1,1] State B
 * [1,1,1,1,0] State B
 * [1,1,1,0,0] State B
 * [1,1,0,0,0] State B
 * [1,0,0,0,0] State B
 * [0,0,0,0,0] State B
 * [0,0,0,0,0] State A
 */

import com.cognitionbox.petra.ast.terms.Entry;
import com.cognitionbox.petra.ast.terms.External;
import com.cognitionbox.petra.examples.turingmachine.external.Tape;
import com.cognitionbox.petra.examples.turingmachine.objects.*;

import static com.cognitionbox.petra.ast.interp.util.Program.seq;

@Entry
public class TuringMachine implements Runnable {

    @External
    private final Tape tape = new Tape();

    private final State state = new State();
    private final HeadMove headMove = new HeadMove(tape);
    private final HeadDir headDir = new HeadDir(tape);
    private final HeadVal headVal = new HeadVal(tape);

    public boolean start(){return state.isA() && headMove.movedNot() && headDir.moveRightOnly() && headVal.headUnkown();}

    public boolean a(){return state.isA() && headMove.movedRight() && headDir.moveRightOnly() && headVal.headTrue();}
    public boolean b(){return state.isA() && headMove.movedRight() && headDir.moveLeftOnly() && headVal.headTrue();}
    public boolean c(){return state.isB() && headMove.movedLeft() && headDir.moveLeftOnly() && headVal.headFalse();}
    public boolean d(){return state.isB() && headMove.movedLeft() && headDir.moveRightOnly() && headVal.headFalse();}

    @Entry public void run(){
        if (start()) {
            seq(headMove::moveRight,headVal::writeTrue);
            assert (a());
        } else if (a()){
            seq(headMove::moveRight,headVal::writeTrue);
            assert (a() ^ b());
        } else if (b()){
            seq(state::setB, headMove::moveLeft,headVal::writeFalse);
            assert (c());
        } else if (c()){
            seq(headMove::moveLeft,headVal::writeFalse);
            assert (c() ^ d());
        } else if (d()){
            seq(state::setA,headMove::moveRight,headVal::writeTrue);
            assert (a());
        }
    }
}
