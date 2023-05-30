package droneroutesystem;

import ast.terms.Base;

@Base
public class Battery {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	public boolean returnHomeLevel() { return connection.returnHomeLevel(); }
	public boolean okLevel() { return connection.okLevel(); }

	public void read(){
		if (returnHomeLevel() ^ okLevel()){
			connection.readBattery();
			assert(returnHomeLevel() ^ okLevel());
		}
	}

}