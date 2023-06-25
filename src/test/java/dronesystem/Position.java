package dronesystem;

import ast.terms.Base;
import ast.terms.Initial;
import droneroutesystem.Waypoint;

@Base
public class Position {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	public boolean onLand(){ return connection.getX() != 0 && connection.getY() != 0 && connection.getZ() == 0; }

	public boolean inAir(){ return connection.getZ() == 100; }

	@Initial
	public boolean atHome(){ return connection.getX() == 0 && connection.getY() == 0 && connection.getZ() == 0; }

	public void travelToHomeAndWaitTillHome() {
		if (onLand() ^ inAir()){
			System.out.println("travelToHomeAndWaitTillHome");
			connection.goToXYZ(0, 0, 0);
			while(!atHome()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert(atHome());
		}
	}

	public void landAndWaitTillLanded() {
		if (onLand() ^ inAir()){
			System.out.println("landAndWaitTillLanded");
			connection.goToXYZ(connection.getX(), connection.getY(), 0);
			while(!onLand()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert(onLand());
		}
	}

	public void takeOffAndWaitTillInAir() {
		if (onLand()){
			System.out.println("takeOffAndWaitTillInAir");
			connection.goToXYZ(connection.getX(), connection.getY(), 100);
			while(!inAir()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert(inAir());
		}
	}
}