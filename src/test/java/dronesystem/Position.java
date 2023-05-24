package dronesystem;

import ast.terms.Base;

@Base
public class Position {
	private final DroneConnection connection = new DroneConnection();
	public boolean onLand(){ return connection.getZ() == 0; }
	public boolean inAir() {
		return connection.getZ() > 0;
	}
}