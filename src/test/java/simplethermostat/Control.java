package simplethermostat;

import ast.terms.Base;

@Base
public class Control {
	private volatile boolean active = true;
	public boolean on() { return active; }
	public boolean off() { return !active; }

	public void turnOn() {
		if (on() ^ off()){
			System.out.println("turnOn");
			active = true;
			assert(on());
		}
	}

	public void turnOff() {
		if (on() ^ off()){
			System.out.println("turnOff");
			active = false;
			assert(off());
		}
	}
}