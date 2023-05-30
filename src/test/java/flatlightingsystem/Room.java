package flatlightingsystem;

import static ast.interp.util.Program.par;

public class Room {
	private final Light centre = new Light();
	private final Light side = new Light();

	public boolean bothOn() { return centre.on() && side.on(); }
	public boolean bothOff() { return centre.off() && side.off(); }

	public boolean other() { return !(centre.on() && side.on()) && !(centre.off() && side.off()); }

	public void toggle() {
		if (bothOff()){
			centre.turnOn();
			side.turnOn();
			assert(bothOn());
		}
		if (bothOn()){
			par(()-> centre.turnOff(),
				()-> side.turnOff());
			assert(bothOff());
		}
	}

	public void turnOff() {
		if (other()){
			centre.turnOff();
			side.turnOff();
			assert(bothOff());
		}
	}
}