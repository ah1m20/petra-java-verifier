package com.cognitionbox.petra.meanreversion;

import static com.cognitionbox.petra.ast.interp.util.Program.startReactive;

public class Main {
    public static void main(String[] args) {
        startReactive(0,new MeanRev(), MarketData.getInstance());
    }
}
