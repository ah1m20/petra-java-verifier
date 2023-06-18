package hsm;

public class MachineB implements Runnable {
    private final ExternalUncontrolledValues externalUncontrolledValues = ExternalUncontrolledValues.getInstance();
    private final Control control = new Control();

    public boolean a(){return externalUncontrolledValues.highX() && control.on();}
    public boolean b(){return externalUncontrolledValues.highX() && control.on();}
    public boolean c(){return externalUncontrolledValues.highX() && control.on();}
    public void run(){
        if (a() ^ b() ^ c()){

            assert(c());
        }
    }
}
