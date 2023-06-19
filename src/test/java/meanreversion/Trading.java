package meanreversion;

import ast.terms.Base;

@Base public class Trading {
    private final MarketData marketData = MarketData.getInstance();
    public boolean midAboveSMA(){return true;}
    public boolean midBelowSMA(){return true;}
}
