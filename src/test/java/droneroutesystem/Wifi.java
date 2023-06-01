package droneroutesystem;

import ast.terms.Base;

@Base
public class Wifi {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	public boolean lowSNR() {return connection.lowSNR();}
	public boolean normalSNR() {return connection.normalSNR();}
	public boolean highSNR() {return connection.highSNR();}
}