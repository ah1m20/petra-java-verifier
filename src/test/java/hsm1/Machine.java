package hsm1;

import hsm.MachineA;

/*
 *  The aim of this example is to use Petra to model a general hierarchical state machine
 *  leveraging Petra's compositional approach and to create a scenario where the base objects
 *  read from uncontrolled external values (read-only, from the prespective of within Petra code, but are updated by outside by the main run loop),
 *  such that we create a gap in the observable state space by coding predicates which do not cover the entire private space, in order to see
 *  if a verified system can get into a state in which the system gets stuck loops forever doing nothing (i.e. liveness failure),
 *  because we pushed the system into a state in which cannot be exited from as access to the control is guarded behind the entered state.
 */
public class Machine implements Runnable {
    private final XYZWrapper wrapper = new XYZWrapper();
    private final Control control = new Control();

    public boolean a(){return control.off();}
    public boolean b(){return control.on();}
    public boolean c(){return control.off() && wrapper.x();}

    public void run(){
        if (a()){
            control.turnOn();
            assert(b());
        }
        if (b()){
            ;
            assert(b());
        }
        if (c()){
            ;
            assert(c());
        }
    }
}
