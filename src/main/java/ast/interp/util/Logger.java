package ast.interp.util;

import static ast.interp.util.Collections.set;

public final class Logger {

    private Set<String> logEntries = set();
    public void info(String s) {
        if (!logEntries.contains(s)){
            System.out.println("INFO: "+s);
            logEntries.add(s);
        }
    }
}
