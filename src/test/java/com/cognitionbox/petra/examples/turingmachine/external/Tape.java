package com.cognitionbox.petra.examples.turingmachine.external;

import com.cognitionbox.petra.ast.terms.External;

// Petra coordinates actions of a flat object with all the data and actions.
// this allows the "views" to just be Petra objects which use different parts of the underlying object.
// Think about how we deal with multiple externals over a single database, should it be just one external, whats the appraoch?
@External
public class Tape {
    protected int head = -1;
    protected int lastHead = head;
    protected boolean[] tape = new boolean[5];


    public void moveLeft(){
        lastHead = head;
        head--;
    }

    public void moveRight(){
        lastHead = head;
        head++;
    }

    //@Override
    public boolean movedLeft() {
        return head-lastHead==-1;
    }

    //@Override
    public boolean movedRight() {
        return head-lastHead==1;
    }

    //@Override
    public boolean notMoved() {
        return head-lastHead==0;
    }

    public boolean moveLeftOnly() {
        return head > 0;
    }

    public boolean moveRightOnly() {
        return head < tape.length - 1;
    }

    public void writeTrue(){
        if (headTrue() ^ headFalse()){
            tape[head] = true;
            for (boolean b : tape){
                System.out.print(b+" ");
            }
            System.out.println();
            assert(headTrue());
        }
    }

    public void writeFalse(){
        if (headTrue() ^ headFalse()){
            tape[head] = false;
            for (boolean b : tape){
                System.out.print(b+" ");
            }
            System.out.println();
            assert(headFalse());
        }
    }

    //@Override
    public boolean headTrue() {
        return tape[head] == true;
    }

    //@Override
    public boolean headFalse() {
        return tape[head] == false;
    }

    public boolean headUnkown() {
        return head==-1;
    }

    public void moveNot() {

    }
}
