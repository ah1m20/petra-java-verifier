package com.cognitionbox.petra.examples.turingmachine2;

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
import com.cognitionbox.petra.examples.turingmachine2.external.Tape;
import com.cognitionbox.petra.examples.turingmachine2.objects.HeadNav;
import com.cognitionbox.petra.examples.turingmachine2.objects.HeadVal;
import com.cognitionbox.petra.examples.turingmachine2.objects.State;

import static com.cognitionbox.petra.ast.interp.util.Program.seq;

@Entry
public class TuringMachine implements Runnable {

    @External
    private final Tape tape = new Tape();

    private final State state = new State();
    private final HeadNav headNav = new HeadNav(tape);
    private final HeadVal headVal = new HeadVal(tape);

    public boolean start(){return state.isA() && headNav.movedNotMoveRight() && headVal.headUnkown();}

    public boolean a(){return state.isA() && headNav.movedRightMoveRight() && headVal.headTrue();}
    public boolean b(){return state.isA() && headNav.movedRightMoveLeft() && headVal.headTrue();}
    public boolean c(){return state.isB() && headNav.movedLeftMoveLeft() && headVal.headFalse();}
    public boolean d(){return state.isB() && headNav.movedLeftMoveRight() && headVal.headFalse();}

    @Entry public void run(){
        if (start()) {
            seq(headNav::moveRight,headVal::writeTrue);
            assert (a());
        } else if (a()){
            seq(headNav::moveRight,headVal::writeTrue);
            assert (a() ^ b());
        } else if (b()){
            seq(state::setB, headNav::moveLeft,headVal::writeFalse);
            assert (c());
        } else if (c()){
            seq(headNav::moveLeft,headVal::writeFalse);
            assert (c() ^ d());
        } else if (d()){
            seq(state::setA,headNav::moveRight,headVal::writeTrue);
            assert (a());
        }
    }
}
