package com.cognitionbox.petra.examples.turingmachine2.objects;

import com.cognitionbox.petra.examples.turingmachine2.external.Tape;


public class HeadNav {
    private final HeadMove headMove;
    private final HeadDir headDir;

    public HeadNav(Tape tape) {
        headMove = new HeadMove(tape);
        headDir = new HeadDir(tape);
    }

    public boolean movedNotMoveRight(){return headMove.movedNot() && headDir.moveRightOnly();}

    public boolean movedNotMoveLeft(){
        return headMove.movedNot() && headDir.moveLeftOnly();
    }

    public boolean movedRightMoveRight(){
        return headMove.movedRight() && headDir.moveRightOnly();
    }

    public boolean movedRightMoveLeft(){
        return headMove.movedRight() && headDir.moveLeftOnly();
    }

    public boolean movedLeftMoveRight(){
        return headMove.movedLeft() && headDir.moveRightOnly();
    }

    public boolean movedLeftMoveLeft(){
        return headMove.movedLeft() && headDir.moveLeftOnly();
    }

    public void moveLeft(){
        if (movedNotMoveLeft() ^
                movedRightMoveLeft() ^
                movedLeftMoveLeft()){
            headMove.moveLeft();
            assert(movedLeftMoveRight() ^ movedLeftMoveLeft());
        }
    }

    public void moveRight(){
        if (movedNotMoveRight() ^
                movedRightMoveRight() ^
                movedLeftMoveRight()){
            headMove.moveRight();
            assert(movedRightMoveRight() ^ movedRightMoveLeft());
        }
    }

    public void moveNot(){
        if (movedNotMoveRight() ^
                movedNotMoveLeft() ^
                movedRightMoveRight() ^
                movedRightMoveLeft() ^
                movedLeftMoveRight() ^
                movedLeftMoveLeft()){
            headMove.moveNot();
            assert(movedNotMoveRight() ^ movedNotMoveLeft());
        }
    }
}
