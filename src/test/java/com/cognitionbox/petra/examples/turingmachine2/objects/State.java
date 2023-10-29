package com.cognitionbox.petra.examples.turingmachine2.objects;

import com.cognitionbox.petra.ast.terms.Base;

@Base
public class State {

    private enum StateType {
        A,B
    }

    private StateType stateType = StateType.A;

    public boolean isA(){return stateType==StateType.A;}
    public boolean isB(){return stateType==StateType.B;}

    public void setA(){
        if (isA() ^ isB()){
            this.stateType = StateType.A;
            System.out.println("State A.");
            assert(isA());
        }
    }

    public void setB(){
        if (isA() ^ isB()){
            this.stateType = StateType.B;
            System.out.println("State B.");
            assert(isB());
        }
    }
}
