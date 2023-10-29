package com.cognitionbox.petra.examples.buildingalarmsystem;

import com.cognitionbox.petra.ast.terms.Base;

@Base public class AlarmSensor {
	private final Bool power = new Bool();
	private final Bool control = new Bool();

	public boolean armed() { return power.isTrue() && control.isFalse(); }
	public boolean disarmed() { return power.isFalse() || control.isFalse(); }

	public void arm() {
		if (armed() ^ disarmed()){
			power.setTrue();
			power.setTrue();
			assert(armed());
		}
	}

	public void disarm() {
		if (armed() ^ disarmed()){
			power.setFalse();
			power.setFalse();
			assert(disarmed());
		}
	}
}