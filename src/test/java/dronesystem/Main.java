package dronesystem;

import static ast.interp.util.Program.mainLoop;

public class Main {
    public static void main(String[] args){
        mainLoop(0,new Controller(),DroneConnection.getDroneConnection());
    }
}
