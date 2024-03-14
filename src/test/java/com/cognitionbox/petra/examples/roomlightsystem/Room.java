package com.cognitionbox.petra.examples.roomlightsystem;

import com.cognitionbox.petra.ast.terms.Entry;

import static com.cognitionbox.petra.ast.interp.util.Program.par;

@Entry
public class Room implements Runnable {
	private final Light centre = new Light();
	private final Light side = new Light();
	public boolean both() { return centre.on() && side.on(); }
	public boolean none() { return centre.off() && side.off(); }

	public boolean other() { return !(centre.on() && side.on()) && !(centre.off() && side.off()); }

	@Entry public void run() {
		if (other()){
			;
			assert(other());
		} else if (none()){
//			centre.turnOn();
//			side.turnOn();
			par(()-> centre.turnOn(),
					()-> side.turnOn());
			assert(both());
		} else if (both()){
			par(()-> centre.turnOff(),
				()-> side.turnOff());
			assert(none());
		}
	}
}