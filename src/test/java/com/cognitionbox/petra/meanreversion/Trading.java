package com.cognitionbox.petra.meanreversion;

import com.cognitionbox.petra.ast.terms.Base;

@Base public class Trading {
    private final MarketData marketData = MarketData.getInstance();
    public boolean midAboveSMA(){return true;}
    public boolean midBelowSMA(){return true;}
}
