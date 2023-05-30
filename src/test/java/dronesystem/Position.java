package dronesystem;

import ast.terms.Base;

@Base
public class Position {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	public boolean onLand(){ return connection.getX() != 0 && connection.getY() != 0 && connection.getZ() == 0; }

	public boolean inAir(){ return connection.getZ() > 0; }

	public boolean atHome(){ return connection.getX() == 0 && connection.getY() == 0 && connection.getZ() == 0; }

	public void waitUntilHome(){
		if (inAir()){
			System.out.println("waitUntilHome");
			while(!atHome()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert (atHome());
		}
	}

	public void waitUntilLanded(){
		if (inAir()){
			System.out.println("waitUntilLanded");
			while(!onLand()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert (onLand());
		}
	}

	public void waitUntilInAir(){
		if (onLand()){
			System.out.println("onLand");
			while(!inAir()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert (inAir());
		}
	}
}