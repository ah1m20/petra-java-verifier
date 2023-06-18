package hsm;

import droneroutesystem.Controller;
import droneroutesystem.DroneConnection;

import static ast.interp.util.Program.mainLoop;

public class Main {
    public static void main(String[] args) {
        mainLoop(0,new MachineRoot(), ExternalUncontrolledValues.getInstance());
    }
}
