package com.cognitionbox.petra.examples.tradingsystem;

import com.cognitionbox.petra.ast.terms.Base;

@Base
public class Decision {

    private final DecisionFlag decisionFlag = new DecisionFlag();

    public boolean start() { return decisionFlag.start(); }
    public boolean buyOpened()  { return decisionFlag.buyOpened(); }
    public boolean sellOpened() { return decisionFlag.sellOpened(); }

    public boolean closed() { return decisionFlag.closed(); }

    public void setClose() {
        if (buyOpened() ^ sellOpened()){
            decisionFlag.setClosed();
            assert(closed());
        }
    }

    public void setOpenBuy(){
        if (start() ^ closed()){
            decisionFlag.setBuyOpenned();
            assert(buyOpened());
        }
    }
    public void setOpenSell() {
        if (start() ^ closed()){
            decisionFlag.setSellOpenned();
            assert(sellOpened());
        }
    }
}