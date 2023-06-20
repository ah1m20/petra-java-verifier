package hsm1;

import ast.terms.Base;
import simplethermostat.Bool;

@Base
public class M3 {
	private final Bool bool = new Bool();
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