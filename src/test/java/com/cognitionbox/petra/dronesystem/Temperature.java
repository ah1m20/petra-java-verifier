package com.cognitionbox.petra.dronesystem;
import com.cognitionbox.petra.ast.terms.Base;
@Base public class Temperature {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	public boolean low() { return connection.low(); }
	public boolean normal() { return connection.normal(); }
	public boolean high() { return connection.high(); }
}