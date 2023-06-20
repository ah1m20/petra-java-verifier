package droneroutesystem;
import ast.terms.Base;
import ast.terms.Initial;

@Base public class Temperature {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	@Initial
	public boolean low() { return connection.low(); }
	public boolean normal() { return connection.normal(); }
	public boolean high() { return connection.high(); }

}