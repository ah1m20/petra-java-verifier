package smarthomesystem;

import static ast.interp.util.Program.par;

public class RoomLighting {
	private final Light centre = new Light();
	private final Light side = new Light();

	public boolean on() { return centre.on() && side.on(); }
	public boolean off() { return centre.off() && side.off(); }

	public void toggle() {
		if (off()){
			centre.turnOn();
			side.turnOn();
			assert(on());
		}
		if (on()){
			par(()-> centre.turnOff(),
				()-> side.turnOff());
			assert(off());
		}
	}
}