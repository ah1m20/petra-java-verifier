package com.cognitionbox.petra.dronesystem;

import com.cognitionbox.petra.ast.terms.Base;

@Base
public class Wifi {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	public boolean lowSNR() {return connection.lowSNR();}
	public boolean normalSNR() {return connection.normalSNR();}
	public boolean highSNR() {return connection.highSNR();}
}