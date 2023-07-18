package com.cognitionbox.petra.droneroutesystem;

import com.cognitionbox.petra.ast.terms.Initial;

public class Diagnostics {
    private final TemperatureTrigger temperatureTrigger = new TemperatureTrigger();
    private final FlyHomeTrigger flyHomeTrigger = new FlyHomeTrigger();
    @Initial
    public boolean ok(){return temperatureTrigger.disabled() && flyHomeTrigger.disabled();}

    /* Adding && flyHomeTrigger.disabled() to the predicate below causes a gap in the coverage and
     * hence if not caught can cause both reachability/liveness issues, hence why the additional complete coverage
     * of predicates wrt to the underlying state space has been added as a requirement to all objects.
     * Currently, the check is also place at the root object, this can be removed as checking all objects covers this and
     * so all that is needed at the root object is to check the public symbolic state space is covered by the entry point.
     */
    public boolean temperatureWarning(){return temperatureTrigger.enabled();}

    public boolean flyHomeImmediately(){return temperatureTrigger.disabled() && flyHomeTrigger.enabled();}
}
