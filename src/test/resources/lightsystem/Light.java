public class Light {
	private final Power power = new Power();
	private final Control control = new Control();

	public boolean on() { return power.on() && control.on(); }
	public boolean off() { return power.off() || control.off(); }

	public void toggle() {
		if (off()){
			power.turnOn();
			control.turnOn();
			assert(on());
		}
		if (on()){
			par(()->power.turnOff(),
				()->control.turnOff());
			assert(off());
		}
	}
}