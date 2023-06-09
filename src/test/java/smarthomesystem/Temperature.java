package smarthomesystem;
import ast.terms.Base;
import dronesystem.DroneConnection;

@Base public class Temperature {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	public boolean low() { return connection.getTemp() < 30; }
	public boolean normal() { return connection.getTemp() >= 30 && connection.getTemp() <= 70; }
	public boolean high() { return connection.getTemp() > 70; }

	public float getCurrent() {
		return 0;
	}

	public float getTarget() {
		return 0;
	}
}