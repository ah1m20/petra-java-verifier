package com.cognitionbox.petra.dronesystem;

import com.cognitionbox.petra.ast.terms.Base;

@Base
public class Battery {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	public boolean returnHomeLevel() { return connection.returnHomeLevel(); }
	public boolean okLevel() { return connection.okLevel(); }
}