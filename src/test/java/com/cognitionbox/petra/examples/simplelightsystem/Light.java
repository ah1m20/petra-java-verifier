package com.cognitionbox.petra.examples.simplelightsystem;

import static com.cognitionbox.petra.ast.interp.util.Program.par;
import com.cognitionbox.petra.ast.terms.Initial;

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