package hsm;

public class MachineA {

    private final MachineB machineB = new MachineB();
    private final Control control = new Control();

    public boolean a(){return machineB.a() && control.on();}
    public boolean b(){return machineB.b() && control.on();}
    public boolean c(){return machineB.c() && control.on();}
    public void transitionA1(){
        if (a() ^ b() ^ c()){

            assert(c());
        }
    }
}
