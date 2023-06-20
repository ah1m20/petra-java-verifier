package hsm1;

import ast.terms.Base;
import simplethermostat.Bool;

/*
 *  The aim of this example is to use Petra to model a general hierarchical state machine
 *  leveraging Petra's compositional approach and to create a scenario where the base objects
 *  read from uncontrolled external values (read-only, from the prespective of within Petra code, but are updated by outside by the main run loop),
 *  such that we create a gap in the observable state space by coding predicates which do not cover the entire private space, in order to see
 *  if a verified system can get into a state in which the system gets stuck loops forever doing nothing (i.e. liveness failure),
 *  because we pushed the system into a state in which cannot be exited from as access to the control is guarded behind the entered state.
 */
@Base public class M2 {
    private final Bool bool = new Bool();
    public boolean on() { return bool.isTrue(); }
    public boolean off() { return bool.isFalse(); }

    public void turnOn() {
        if (on() ^ off()){
            bool.setTrue();
            assert(on());
        }
    }

    public void turnOff() {
        if (on() ^ off()){
            bool.setFalse();
            assert(off());
        }
    }
}
