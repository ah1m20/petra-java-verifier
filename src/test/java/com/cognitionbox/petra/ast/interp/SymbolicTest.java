package com.cognitionbox.petra.ast.interp;

import com.cognitionbox.petra.ast.interp.Symbolic;
import com.cognitionbox.petra.ast.terms.Prog;
import com.google.gson.Gson;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.cognitionbox.petra.ast.interp.util.Program.buildProgram;
import static org.junit.Assert.assertTrue;

public class SymbolicTest {

    @Test
    public void testSimpleLightSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/com/cognitionbox/petra/examples/simplelightsystem/","run","Light","Main","Light","Power","Control");
        Symbolic symbolic = new Symbolic(prog);
        //symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testDualLightSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/com/cognitionbox/petra/examples/duallightingsystem/","run","Light","Main","Light","Power","Control");
        Symbolic symbolic = new Symbolic(prog);
        System.out.println(new Gson().toJson(symbolic.lookupObj(prog.getAepsilon())));
        //symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testFlatLightingSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/com/cognitionbox/petra/examples/buildingalarmsystem/","run","Building","Main","Building","Room","AlarmSensor");
        Symbolic symbolic = new Symbolic(prog);
        //symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testDroneRouteSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/com/cognitionbox/petra/examples/droneroutesystem/","run","Controller","Main","Controller","RoutePlan","Battery","Temperature","Wifi","Position","Control","FlyHomeTrigger","TemperatureTrigger","Diagnostics","SysWrapper");
        Symbolic symbolic = new Symbolic(prog);
        //symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testSimplethermostat() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/com/cognitionbox/petra/examples/simplethermostat/","run","Thermostat","Main","Thermostat","Temperature","Control","Logging");
        Symbolic symbolic = new Symbolic(prog);
        //symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }
}
