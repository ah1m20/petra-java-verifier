package droneroutesystem;

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
		if (on()){
			System.out.println("turnOff");
			active = false;
			assert(off());
		}
	}

	public void exit() {
		if (off()){
			System.exit(0);
			assert(off());
		}
	}

	public void logFlyHome() {
		if (on()){
			System.out.println("flyHome.");
			assert(on());
		}
	}

	public void logLand() {
		if (on()){
			System.out.println("land.");
			assert(on());
		}
	}

	public void logRC() {
		if (on()){
			System.out.println("rc.");
			assert(on());
		}
	}
}