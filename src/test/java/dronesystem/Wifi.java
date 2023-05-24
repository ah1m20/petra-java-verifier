package dronesystem;

import ast.terms.Base;

@Base
public class Wifi {
	private final DroneConnection connection = new DroneConnection();
	public boolean lowSNR() {return connection.getSNR() < 0.2;}
	public boolean normalSNR() {return connection.getSNR() >= 0.2 && connection.getSNR() <= 0.6;}
	public boolean highSNR() {return connection.getSNR() > 0.6;}
}