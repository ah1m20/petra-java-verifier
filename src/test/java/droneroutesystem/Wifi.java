package droneroutesystem;

import ast.terms.Base;
import ast.terms.Initial;

@Base
public class Wifi {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	public boolean lowSNR() {return connection.lowSNR();}
	public boolean normalSNR() {return connection.normalSNR();}
	@Initial
	public boolean highSNR() {return connection.highSNR();}
}