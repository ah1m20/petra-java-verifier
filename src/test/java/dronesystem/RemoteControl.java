package dronesystem;
import ast.terms.Base;

@Base public class RemoteControl {

	private final DroneConnection droneConnection = DroneConnection.getDroneConnection();

	public boolean forward(){return droneConnection.forward();}

	public boolean back(){return droneConnection.back();}

	public boolean left(){return droneConnection.left();}

	public boolean right(){return droneConnection.right();}

	public void processCommand(){
		if (forward()){
			System.out.println("forward");
			droneConnection.readRc();
			assert(forward() ^ back() ^ left() ^ right());
		}
		if (back()){
			System.out.println("back");
			droneConnection.readRc();
			assert(forward() ^ back() ^ left() ^ right());
		}
		if (left()){
			System.out.println("left");
			droneConnection.readRc();
			assert(forward() ^ back() ^ left() ^ right());
		}
		if (right()){
			System.out.println("right");
			droneConnection.readRc();
			assert(forward() ^ back() ^ left() ^ right());
		}
	}

}