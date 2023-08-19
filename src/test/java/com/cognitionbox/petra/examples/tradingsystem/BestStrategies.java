package com.cognitionbox.petra.examples.tradingsystem;

import com.cognitionbox.petra.ast.terms.Base;

@Base
public final class BestStrategies {
    private final PositionsData positionsData = new PositionsData();

    public boolean meanRevIsBest(){
        return true;//positionsData.getTopPerformingStrategy()==StrategyFlag.StrategyEnum.MEANREV;
    }

    public boolean trendIsBest(){
        return true;//positionsData.getTopPerformingStrategy()==StrategyFlag.StrategyEnum.TREND;
    }
}
