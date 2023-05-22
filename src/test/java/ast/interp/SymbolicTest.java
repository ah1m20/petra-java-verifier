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
        Prog prog = buildProgram("/lightsystem/","toggle","Light","Light","Power","Control");
        Symbolic symbolic = new Symbolic(prog);
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testFlatLightingSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("/flatlightingsystem/","toggle","Flat","Flat","Room","Light");
        Symbolic symbolic = new Symbolic(prog);
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testDroneSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("/dronesystem/","action","Controller","Controller","Diagnostics","Navigation","RoutePlan","Battery","Temperature","Wifi","Barometer");
        Symbolic symbolic = new Symbolic(prog);
        symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }
}
