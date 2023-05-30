package ast.interp;

import ast.terms.Prog;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static ast.interp.util.Program.buildProgram;
import static org.junit.Assert.assertTrue;

public class SymbolicTest {

    @Test
    public void testLightSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/lightsystem/","toggle","Light","Light","Power","Control");
        Symbolic symbolic = new Symbolic(prog);
        symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testFlatLightingSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/flatlightingsystem/","toggle","Flat","Flat","Room","Light");
        Symbolic symbolic = new Symbolic(prog);
        symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testDroneSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/dronesystem/","action","Controller","Controller","RoutePlan","Battery","Temperature","Wifi","Position","Flight","Control","FlyHome","Land","AutoPilot","RemoteControl");
        Symbolic symbolic = new Symbolic(prog);
        symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testSimplethermostat() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/simplethermostat/","action","Thermostat","Thermostat","Temperature","Control","Logging");
        Symbolic symbolic = new Symbolic(prog);
        symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }
}
