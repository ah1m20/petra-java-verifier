package flatlightingsystem;

import ast.terms.Base;

@Base public class Light {
	private volatile boolean power = false;
	private volatile boolean control = false;

	public boolean on() { return power && control; }
	public boolean off() { return !power || !control; }

	public void turnOn() {
		if (on() ^ off()){
			power = true;
			control = true;
			assert(on());
		}
	}

	public void turnOff() {
		if (on() ^ off()){
			power = false;
			control = false;
			assert(off());
		}
	}
}