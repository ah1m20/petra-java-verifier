package com.cognitionbox.petra.examples.buildingalarmsystem;

import static com.cognitionbox.petra.ast.interp.util.Program.par;

public class Room {
	private final AlarmSensor centre = new AlarmSensor();
	private final AlarmSensor side = new AlarmSensor();

	public boolean bothArmed() { return centre.armed() && side.armed(); }
	public boolean bothDisarmed() { return centre.disarmed() && side.disarmed(); }

	public void toggle() {
		if (bothDisarmed()){
			centre.arm();
			side.arm();
			assert(bothArmed());
		} else if (bothArmed()){
			par(()-> centre.disarm(),
				()-> side.disarm());
			assert(bothDisarmed());
		}
	}

	public void disarm() {
		if (bothArmed() ^ bothDisarmed()){
			centre.disarm();
			side.disarm();
			assert(bothDisarmed());
		}
	}
}