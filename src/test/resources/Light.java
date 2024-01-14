package com.cognitionbox.petra.examples.lightsystem;

import com.cognitionbox.petra.ast.terms.Initial;

import static com.cognitionbox.petra.ast.interp.util.Program.par;

public class Light implements Runnable {
	private final Power power = new Power();
	private final Control control = new Control();

	public boolean on() { return power.on() && control.on(); }
	@Initial public boolean off() { return power.off() || control.off(); }

	public void run() {
		if (off()){
			power.turnOn();
			control.turnOn();
			assert(on());
		} else if (on()){
			par(power::turnOff, control::turnOff);
			assert(off());
		}
	}
}