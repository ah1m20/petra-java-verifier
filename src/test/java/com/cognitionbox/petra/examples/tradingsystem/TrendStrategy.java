package com.cognitionbox.petra.examples.tradingsystem;

// 2 x 4 = 8
public class TrendStrategy implements Runnable{
    private final SMASignal smaSignal = new SMASignal(); // 2
    private final Decision decision = new Decision(); // 4

    public boolean openBuy(){return (decision.start() || decision.closed()) && smaSignal.midAboveSma();} // 2
    public boolean openSell(){return (decision.start() || decision.closed()) && smaSignal.midBelowSma();} // 2

    public boolean openedBuy(){
        return decision.buyOpened() && smaSignal.midAboveSma();
    } // 1

    public boolean openedSell(){
        return decision.sellOpened() && smaSignal.midBelowSma();
    } // 1

    public boolean close(){
        return (decision.buyOpened() && smaSignal.midBelowSma()) || (decision.sellOpened() && smaSignal.midAboveSma()); // 2
    }

    @Override
    public void run() {
        if (openBuy()){
            decision.setOpenBuy();
            assert(openedBuy());
        } else if (openSell()){
            decision.setOpenSell();
            assert(openedSell());
        } else if (openedBuy() ^ openedSell()){
            ; // debug this
            assert(openedBuy() ^ openedSell());
        } else if (close()){
            decision.setClose();
            assert(openBuy() ^ openSell());
        }
    }
}
