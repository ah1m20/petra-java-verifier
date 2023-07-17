package ast.interp;

import ast.terms.Prog;
import com.google.gson.Gson;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static ast.interp.util.Program.buildProgram;
import static org.junit.Assert.assertTrue;

public class SymbolicTest {

    @Test
    public void testLightSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/lightsystem/","run","Light","Main","Light","Power","Control");
        Symbolic symbolic = new Symbolic(prog);
        //symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testLightSystem2() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/lightingsystem2/","run","Light","Main","Light","Power","Control");
        Symbolic symbolic = new Symbolic(prog);
        System.out.println(new Gson().toJson(symbolic.lookupObj(prog.getAepsilon())));
        //symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testFlatLightingSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/flatlightingsystem/","run","Flat","Main","Flat","Room","Light");
        Symbolic symbolic = new Symbolic(prog);
        //symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Ignore
    @Test
    public void testDroneSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/dronesystem/","run","Controller","Main","Controller","Battery","Temperature","Wifi","Position","Controls","Power","FlyHome","Land","AutoPilot","RemoteControl");
        Symbolic symbolic = new Symbolic(prog);
        //symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testDroneRouteSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/droneroutesystem/","run","Controller","Main","Controller","RoutePlan","Battery","Temperature","Wifi","Position","Control","FlyHomeTrigger","TemperatureTrigger","Diagnostics","SysWrapper");
        Symbolic symbolic = new Symbolic(prog);
        //symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testSimplethermostat() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/simplethermostat/","run","Thermostat","Main","Thermostat","Temperature","Control","Logging");
        Symbolic symbolic = new Symbolic(prog);
        //symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test
    public void testEnergyManagementSystem() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/energymanagement/","run","EnergyManagement","Main","EnergyManagement","Library","ZeplerBuilding","Floor1","AirConditioners","Appliances","Heaters","Plumbing","ComputerRoom","ComputerNetwork","ComputerRoom","Logger");
        Symbolic symbolic = new Symbolic(prog);
        //symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }

    @Test public void testMeanRev() throws URISyntaxException, IOException {
        Prog prog = buildProgram("src/test/java/meanreversion/","run","MeanRev","Main","MeanRev","PositionManager","RevOrTrend","Trading");
        Symbolic symbolic = new Symbolic(prog);
        //symbolic.printOutput();
        assertTrue(symbolic.interpProg(prog).isPresent());
    }
}
