package com.cognitionbox.petra.ast.interp.util.loggers;

import java.util.stream.IntStream;

public final class Logger {
    public void info(String s) {
        String prefix = Thread.currentThread().getStackTrace()[4].toString();
        int diff = 100-prefix.length();
        StringBuilder sb = new StringBuilder();
        IntStream.range(0,diff).forEach(i->sb.append(" "));
        System.out.println("INFO:"+prefix+sb+s);
    }

    public void debug(String s) {
        String prefix = Thread.currentThread().getStackTrace()[4].toString();
        int diff = 100-prefix.length();
        StringBuilder sb = new StringBuilder();
        IntStream.range(0,diff).forEach(i->sb.append(" "));
        System.out.println("DEBUG:"+prefix+sb+s);
    }
}
