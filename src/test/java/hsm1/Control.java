package hsm1;

import ast.terms.Base;

@Base
public class Control {
	private volatile boolean active = false;
	public boolean on() { return active; }
	public boolean off() { return !active; }

	public void turnOn() {
		if (on() ^ off()){
			active = true;
			assert(on());
		}
	}

	public void turnOff() {
		if (on() ^ off()){
			active = false;
			assert(off());
		}
	}
}