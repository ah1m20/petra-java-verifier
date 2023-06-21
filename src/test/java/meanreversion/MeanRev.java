package meanreversion;

public class MeanRev implements Runnable{
    private final RevOrTrend revOrTrend = new RevOrTrend();
    private final Trading trading = new Trading();

    private final PositionManager positionManager = new PositionManager();

    public boolean doOpenBuy(){return
            ((positionManager.start() || positionManager.buyClosed() || positionManager.sellClosed()) && revOrTrend.trend() && trading.midAboveSMA()) ||
            ((positionManager.start() || positionManager.buyClosed() || positionManager.sellClosed()) && revOrTrend.reversion() && trading.midBelowSMA());}
    public boolean doOpenSell(){return
            ((positionManager.start() || positionManager.buyClosed() || positionManager.sellClosed()) && revOrTrend.reversion() && trading.midAboveSMA()) ||
            ((positionManager.start() || positionManager.buyClosed() || positionManager.sellClosed()) && revOrTrend.trend() && trading.midBelowSMA());
    }

    public boolean buyOpenned(){
        return (positionManager.buyOpened() && revOrTrend.trend() && trading.midAboveSMA()) ||
                (positionManager.buyOpened() && revOrTrend.reversion() && trading.midBelowSMA());
    }

    public boolean sellOpenned(){
        return (positionManager.sellOpened() && revOrTrend.trend() && trading.midBelowSMA()) ||
                (positionManager.sellOpened() && revOrTrend.reversion() && trading.midBelowSMA());
    }

    public boolean buyClosed(){
        return (positionManager.buyClosed() && revOrTrend.trend() && trading.midAboveSMA()) ||
                (positionManager.buyClosed() && revOrTrend.reversion() && trading.midBelowSMA());
    }

    public boolean sellClosed(){
        return (positionManager.sellClosed() && revOrTrend.trend() && trading.midBelowSMA()) ||
                (positionManager.sellClosed() && revOrTrend.reversion() && trading.midBelowSMA());
    }

    public boolean doCloseBuy(){
        return (positionManager.buyOpened() && revOrTrend.reversion() && trading.midAboveSMA()) ||
                (positionManager.buyOpened() && revOrTrend.trend() && trading.midBelowSMA());
    }

    public boolean doCloseSell(){
        return (positionManager.sellOpened() && revOrTrend.trend() && trading.midAboveSMA()) ||
                (positionManager.sellOpened() && revOrTrend.reversion() && trading.midBelowSMA());
    }

    public boolean doHold(){
        return !(((positionManager.start() || positionManager.buyClosed() || positionManager.sellClosed()) && revOrTrend.trend() && trading.midAboveSMA()) ||
                ((positionManager.start() || positionManager.buyClosed() || positionManager.sellClosed()) && revOrTrend.reversion() && trading.midBelowSMA())) ||

                !(((positionManager.start() || positionManager.buyClosed() || positionManager.sellClosed()) && revOrTrend.reversion() && trading.midAboveSMA()) ||
                        ((positionManager.start() || positionManager.buyClosed() || positionManager.sellClosed()) && revOrTrend.trend() && trading.midBelowSMA())) ||

                !((positionManager.buyOpened() && revOrTrend.trend() && trading.midAboveSMA()) ||
                        (positionManager.buyOpened() && revOrTrend.reversion() && trading.midBelowSMA())) ||

                !((positionManager.sellOpened() && revOrTrend.trend() && trading.midBelowSMA()) ||
                        (positionManager.sellOpened() && revOrTrend.reversion() && trading.midBelowSMA())) ||

                !((positionManager.buyClosed() && revOrTrend.trend() && trading.midAboveSMA()) ||
                        (positionManager.buyClosed() && revOrTrend.reversion() && trading.midBelowSMA())) ||

                !((positionManager.sellOpened() && revOrTrend.trend() && trading.midAboveSMA()) ||
                        (positionManager.sellOpened() && revOrTrend.reversion() && trading.midBelowSMA())) ||

                !((positionManager.buyOpened() && revOrTrend.reversion() && trading.midAboveSMA()) ||
                        (positionManager.buyOpened() && revOrTrend.trend() && trading.midBelowSMA())) ||

                !((positionManager.buyOpened() && revOrTrend.reversion() && trading.midAboveSMA()) ||
                        (positionManager.buyOpened() && revOrTrend.trend() && trading.midBelowSMA())) ||

                !((positionManager.sellOpened() && revOrTrend.trend() && trading.midAboveSMA()) ||
                        (positionManager.sellOpened() && revOrTrend.reversion() && trading.midBelowSMA()));
    }

    @Override
    public void run() {
        if (doHold()){
            ;
            assert(doOpenBuy() ^ doOpenSell() ^ doCloseBuy() ^ doCloseSell() ^ doHold());
        } else if (doOpenBuy()){
           positionManager.setOpenBuy();
           assert(buyOpenned());
        } else if (doOpenSell()){
            positionManager.setOpenSell();
            assert(sellOpenned());
        } else if (doCloseBuy()){
            positionManager.setCloseBuy();
            assert(doHold());
        } else if (doCloseSell()){
            positionManager.setCloseSell();
            assert(doHold());
        }
    }

    /*
     * Need to add a new semantic that accounts for the fact updates to sensors are observable in next iteration, hence
     * predicates hold until next iteration e.g below would not be a valid output:
     * assert(doOpenBuy() ^ doOpenSell() ^ doCloseBuy() ^ doCloseSell() ^ doHold());
     * but below would be:
     * assert(doHold());
     */
}
