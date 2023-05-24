package dronesystem;
import ast.terms.Base;
@Base public class Temperature {
	private final DroneConnection connection = new DroneConnection();
	public boolean low() { return connection.getTemp() < 30; }
	public boolean normal() { return connection.getTemp() >= 30 && connection.getTemp() <= 70; }
	public boolean high() { return connection.getTemp() > 70; }

}