package com.cognitionbox.petra.droneroutesystem;

import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.Initial;

@Base
public class Wifi {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	public boolean lowSNR() {return connection.lowSNR();}
	public boolean normalSNR() {return connection.normalSNR();}
	@Initial
	public boolean highSNR() {return connection.highSNR();}
}