package hsm2;

import ast.terms.Base;

@Base
public class M3 implements Runnable {
    private final Data data = Data.getInstance();

    public boolean a(){return data.highX();}
    public boolean b(){return data.highX();}
    public boolean c(){return data.highX();}
    public void run(){
        if (a() ^ b() ^ c()){
            ;
            assert(c());
        }
    }
}
