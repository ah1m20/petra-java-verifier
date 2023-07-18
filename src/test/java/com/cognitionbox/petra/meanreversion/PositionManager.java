package com.cognitionbox.petra.meanreversion;

import com.cognitionbox.petra.ast.terms.Base;

@Base
public class PositionManager {

    private final State state = new State();

    public boolean start() { return state.start(); }
    public boolean buyOpened()  { return state.buyOpened(); }
    public boolean sellOpened() { return state.sellOpened(); }

    public boolean buyClosed() { return state.buyClosed(); }
    public boolean sellClosed() { return state.sellClosed(); }

    public void setCloseBuy() {
        if (start() ^ buyOpened()){
            state.setBuyClosed();
            assert(buyClosed());
        }
    }
    public void setCloseSell() {
        if (start() ^ sellOpened()){
            state.setSellClosed();
            assert(sellClosed());
        }
    }

    public void setOpenBuy(){
        if (start() ^ buyClosed() ^ sellClosed()){
            state.setBuyOpenned();
            assert(buyOpened());
        }
    }
    public void setOpenSell() {
        if (start() ^ buyClosed() ^ sellClosed()){
            state.setSellOpenned();
            assert(sellOpened());
        }
    }
}