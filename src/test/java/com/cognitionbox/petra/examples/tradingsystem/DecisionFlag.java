package com.cognitionbox.petra.examples.tradingsystem;

import com.cognitionbox.petra.ast.terms.External;

@External
public class DecisionFlag {

    public void setClosed() {
        state = DecisionEnum.CLOSED;
    }

    public void setBuyOpenned() {
        state = DecisionEnum.BUY_OPENNED;
    }

    public void setSellOpenned() {
        state = DecisionEnum.SELL_OPENNED;
    }

    public void setStart() {
        state = DecisionEnum.START;
    }

    private enum DecisionEnum {
        START,
        BUY_OPENNED,
        SELL_OPENNED,
        CLOSED;
    }
    private volatile DecisionEnum state = DecisionEnum.START;

    public boolean start() { return state== DecisionEnum.START; }
    public boolean buyOpened() { return state== DecisionEnum.BUY_OPENNED; }
    public boolean sellOpened() { return state== DecisionEnum.SELL_OPENNED; }
    public boolean closed() { return state== DecisionEnum.CLOSED; }
}
