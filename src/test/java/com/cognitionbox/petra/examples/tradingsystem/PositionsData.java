package com.cognitionbox.petra.examples.tradingsystem;

import com.cognitionbox.petra.ast.terms.External;
import com.cognitionbox.petra.examples.tradingsystem.DecisionFlag;
import com.cognitionbox.petra.examples.tradingsystem.StrategyFlag;

@External
public class PositionsData {

    private final DecisionFlag decisionFlag = new DecisionFlag();

    public boolean start() { return decisionFlag.start(); }
    public boolean buyOpened()  { return decisionFlag.buyOpened(); }
    public boolean sellOpened() { return decisionFlag.sellOpened(); }

    public boolean closed() { return decisionFlag.closed(); }

    public void setCloseBuy() {
        if (start() ^ buyOpened()){
            decisionFlag.setClosed();
            assert(closed());
        }
    }
    public void setCloseSell() {
        if (start() ^ sellOpened()){
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

    public StrategyFlag.StrategyEnum getTopPerformingStrategy() {
        return StrategyFlag.StrategyEnum.MEANREV;
    }
}