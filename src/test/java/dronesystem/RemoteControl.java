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
			System.out.println("RC: forward");
			assert(forward() ^ back() ^ left() ^ right());
		}
		if (back()){
			System.out.println("RC: back");
			assert(forward() ^ back() ^ left() ^ right());
		}
		if (left()){
			System.out.println("RC: left");
			assert(forward() ^ back() ^ left() ^ right());
		}
		if (right()){
			System.out.println("RC: right");
			assert(forward() ^ back() ^ left() ^ right());
		}
	}

}