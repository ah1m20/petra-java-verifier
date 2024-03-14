package com.cognitionbox.petra.examples.roomlightsystem;

import com.cognitionbox.petra.ast.terms.Initial;

public class Light {
	private final Power power = new Power();
	private final Control control = new Control();

	public boolean on() { return power.on() && control.on(); }
	@Initial public boolean off() { return power.off() || control.off(); }

	public void turnOn() {
		if (off()){
			power.turnOn();
			control.turnOn();
			assert(on());
		}
	}

	public void turnOff() {
		if (on()){
			power.turnOff();
			control.turnOff();
			assert(off());
		}
	}
}