package com.cognitionbox.petra.examples.tradingsystem;

public class MacroStrategy implements Runnable {
    private final BestStrategies bestStrategies = new BestStrategies();
    private final MeanRevStrategy meanRevStrategy = new MeanRevStrategy();
    private final TrendStrategy trendStrategy = new TrendStrategy();

    public boolean tradeTrend(){
        return bestStrategies.trendIsBest() && !(meanRevStrategy.openedBuy() ^ meanRevStrategy.openedSell());
    }

    public boolean tradeMeanRev(){
        return bestStrategies.meanRevIsBest() && !(trendStrategy.openedBuy() ^ trendStrategy.openedSell());
    }

    public boolean hold(){
        return (bestStrategies.meanRevIsBest() && (meanRevStrategy.openedBuy() ^ meanRevStrategy.openedSell())) ||
                (bestStrategies.trendIsBest() && (trendStrategy.openedBuy() ^ trendStrategy.openedSell()));
    }

    @Override
    public void run() {
        if (tradeTrend()){
            meanRevStrategy.run();
            assert(tradeTrend() ^ hold());
        } else if (tradeMeanRev()){
            trendStrategy.run();
            assert(tradeTrend() ^ hold());
        }
    }
}
