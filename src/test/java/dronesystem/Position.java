package dronesystem;

import ast.terms.Base;

@Base
public class Position {
	private final DroneConnection connection = new DroneConnection();
	public boolean onLand(){ return connection.getX() != 0 && connection.getY() != 0 && connection.getZ() == 0; }

	public boolean inAir(){ return connection.getZ() > 0; }

	public boolean atHome(){ return connection.getX() == 0 && connection.getY() == 0 && connection.getZ() == 0; }

	public void waitUntilHome(){
		if (inAir()){
			while(!atHome()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert (atHome());
		}
	}

	public void waitUntilLanded(){
		if (inAir()){
			while(!onLand()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert (onLand());
		}
	}
}