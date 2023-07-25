package com.cognitionbox.petra.meanreversion;

public class State {
    public void setBuyClosed() {
        state = StateEnum.BUY_CLOSED;
    }

    public void setSellClosed() {
        state = StateEnum.SELL_CLOSED;
    }

    public void setBuyOpenned() {
        state = StateEnum.BUY_OPENNED;
    }

    public void setSellOpenned() {
        state = StateEnum.SELL_OPENNED;
    }

    public void setSTART() {
        state = StateEnum.START;
    }

    private enum StateEnum {
        START,
        BUY_OPENNED,
        SELL_OPENNED,

        BUY_CLOSED,

        SELL_CLOSED;
    }
    private volatile StateEnum state = StateEnum.START;

    public boolean start() { return state==StateEnum.START; }
    public boolean buyOpened() { return state==StateEnum.BUY_OPENNED; }
    public boolean sellOpened() { return state==StateEnum.SELL_OPENNED; }

    public boolean buyClosed() { return state==StateEnum.BUY_CLOSED; }
    public boolean sellClosed() { return state==StateEnum.SELL_CLOSED; }
}
