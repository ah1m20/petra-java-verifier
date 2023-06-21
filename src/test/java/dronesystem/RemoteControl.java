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
			assert(forward() ^ back() ^ left() ^ right());
		} else if (back()){
			System.out.println("back");
			assert(forward() ^ back() ^ left() ^ right());
		} else if (left()){
			System.out.println("left");
			assert(forward() ^ back() ^ left() ^ right());
		} else if (right()){
			System.out.println("right");
			assert(forward() ^ back() ^ left() ^ right());
		}
	}

}