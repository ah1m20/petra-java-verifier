package meanreversion;

import ast.terms.Base;

@Base
public class PositionManager {
    private enum State {
        START,
        BUY_OPENNED,
        SELL_OPENNED,

        BUY_CLOSED,

        SELL_CLOSED;
    }
    private volatile State state = State.START;
    public boolean start() { return state==State.START; }
    public boolean buyOpened() { return state==State.BUY_OPENNED; }
    public boolean sellOpened() { return state==State.SELL_OPENNED; }

    public boolean buyClosed() { return state==State.BUY_CLOSED; }
    public boolean sellClosed() { return state==State.SELL_CLOSED; }
    public void setCloseBuy() {
        if (start() ^ buyOpened()){
            state = State.BUY_CLOSED;
            assert(buyClosed());
        }
    }
    public void setCloseSell() {
        if (start() ^ sellOpened()){
            state = State.SELL_CLOSED;
            assert(sellClosed());
        }
    }

    public void setOpenBuy(){
        if (start() ^ buyClosed() ^ sellClosed()){
            state = State.BUY_OPENNED;
            assert(buyOpened());
        }
    }
    public void setOpenSell() {
        if (start() ^ buyClosed() ^ sellClosed()){
            state = State.SELL_OPENNED;
            assert(sellOpened());
        }
    }
}