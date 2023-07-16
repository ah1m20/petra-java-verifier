package lightingsystem2;

import ast.terms.Base;
import lightsystem.Bool;

@Base
public class Power {
	private final lightsystem.Bool bool = new Bool();
	public boolean on() { return bool.isTrue(); }
	public boolean off() { return bool.isFalse(); }

	public void turnOn() {
		if (on() ^ off()){
			bool.setTrue();
			assert(on());
		}
	}

	public void turnOff() {
		if (on() ^ off()){
			bool.setFalse();
			assert(off());
		}
	}
}