package com.cognitionbox.petra.examples.tradingsystem;

import com.cognitionbox.petra.ast.terms.Base;

@Base
public class SMASignal {
    private final MarketDataConnection marketDataConnection = MarketDataConnection.getInstance();
    public boolean midAboveSma(){return true;}
    public boolean midBelowSma(){return true;}

    //public boolean noSignal(){return true;}
}
