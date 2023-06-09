package droneroutesystem;

import ast.terms.Base;
import ast.terms.Initial;

@Base
public class Battery {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	public boolean returnHomeLevel() { return connection.returnHomeLevel(); }
	@Initial
	public boolean okLevel() { return connection.okLevel(); }
}