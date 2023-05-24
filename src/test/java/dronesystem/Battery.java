package dronesystem;

import ast.terms.Base;

@Base
public class Battery {
	private final DroneConnection connection = new DroneConnection();
	public boolean returnHomeLevel() { return connection.getPower() < 50; }
	public boolean okLevel() { return connection.getPower() >= 50; }

}