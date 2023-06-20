package droneroutesystem;

import ast.terms.Base;

@Base public class SysWrapper {
    private final Sys sys = new Sys();

    public boolean ok(){return true;}

    public void exit() {
        if (ok()){
            sys.exit();
            assert(ok());
        }
    }

    public void logLand() {
        if (ok()){
            sys.logLand();
            assert(ok());
        }
    }

    public void logRouteActive() {
        if (ok()){
            sys.logRouteActive();
            assert(ok());
        }
    }

}
