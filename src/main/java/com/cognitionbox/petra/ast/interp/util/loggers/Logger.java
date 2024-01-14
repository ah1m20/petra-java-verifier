package com.cognitionbox.petra.ast.interp.util.loggers;

import com.cognitionbox.petra.ast.interp.util.Set;
import com.cognitionbox.petra.ast.terms.Obj;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.cognitionbox.petra.ast.interp.util.Collections.set;

public final class Logger {
    public void info(String s) {
        System.out.println(s); // "INFO: "+
    }


    public <T> void petra(int depth, T t, Obj A, String rule) {

    }


    public void debug(String s) {
        System.out.println("INFO: "+Thread.currentThread().getStackTrace()[3]+s);
    }

    private Set<String> logEntries = set();
    public List<String> logEntriesList = new ArrayList<>();
//    public void info(String s) {
//        if (!logEntries.contains(s)){
//            System.out.println(""+s);
//            logEntries.add(s);
//            logEntriesList.add(s);
//        }
//    }
}
