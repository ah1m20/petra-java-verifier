package com.cognitionbox.petra.smarthomesystem;

import static com.cognitionbox.petra.ast.interp.util.Program.par;

public class RoomHeating {
	private final Heater one = new Heater();

	public boolean on() { return one.on() && one.on(); }
	public boolean off() { return one.off() && one.off(); }

	public void toggle() {
		if (off()){
			one.turnOn();
			one.turnOn();
			assert(on());
		}
		if (on()){
			par(()-> one.turnOff(),
				()-> one.turnOff());
			assert(off());
		}
	}
}