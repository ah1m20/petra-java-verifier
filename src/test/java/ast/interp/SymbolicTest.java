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
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testFlatLightingSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/flatlightingsystem/","toggle","Flat","Flat","Room","Light");
        Symbolic symbolic = new Symbolic(prog);
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testDroneSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/dronesystem/","action","Controller","Controller","Sensors","RoutePlan","Battery","Temperature","Wifi","Position");
        Symbolic symbolic = new Symbolic(prog);
        symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }
}
