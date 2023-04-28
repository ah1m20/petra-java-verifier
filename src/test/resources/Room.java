public class Room {
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
			centre.turnOff();
			side.turnOff();
			assert(off());
		}
	}
}