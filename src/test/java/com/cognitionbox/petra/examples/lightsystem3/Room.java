package com.cognitionbox.petra.examples.lightsystem3;

import static com.cognitionbox.petra.ast.interp.util.Program.par;

public class Room implements Runnable {
	private final Light centre = new Light();
	private final Light side = new Light();
	public boolean both() { return centre.on() && side.on(); }
	public boolean none() { return centre.off() && side.off(); }

	public boolean other() { return !(centre.on() && side.on()) && !(centre.off() && side.off()); }

	public void run() {
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