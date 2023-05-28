package dronesystem;

import ast.terms.Base;

@Base
public class Control {
	private volatile boolean active = false;
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