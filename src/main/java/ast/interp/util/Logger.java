package ast.interp.util;

public final class Logger {
    public void info(String s) {
        System.out.println("INFO: "+Thread.currentThread().getStackTrace()[3]+s);
    }
}
