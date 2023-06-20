package hsm2;

public class M2 {

    private final M3 m3 = new M3();
    private final M4 m4 = new M4();

    public boolean a(){return m3.a() && m4.on();}
    public boolean b(){return m3.b() && m4.on();}
    public boolean c(){return m3.c() && m4.on();}
    public void transition(){
        if (a() ^ b() ^ c()){
            ;
            assert(c());
        }
    }
}
