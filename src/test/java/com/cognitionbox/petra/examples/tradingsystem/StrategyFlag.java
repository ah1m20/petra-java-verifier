package com.cognitionbox.petra.examples.tradingsystem;

import com.cognitionbox.petra.ast.terms.External;

@External
public final class StrategyFlag {
    private StrategyEnum state;
    public boolean meanRevActive(){
        return state== StrategyEnum.MEANREV;
    }
    public boolean trendActive(){
        return state== StrategyEnum.TREND;
    }

    public enum StrategyEnum {
        MEANREV,
        TREND
    }
}
