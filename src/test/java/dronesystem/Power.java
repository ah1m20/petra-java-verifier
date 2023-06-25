package dronesystem;

import ast.terms.Base;
import ast.terms.Initial;

@Base
public class Power {
	private final Sys sys = new Sys();
	private final Bool bool = new Bool(true);

	@Initial
	public boolean on() { return bool.isTrue(); }

	public boolean off() { return bool.isFalse(); }

	public void turnOn() {
		if (on() ^ off()){
			bool.setTrue();
			sys.logTurnOn();
			assert(on());
		}
	}

	public void turnOff() {
		if (on() ^ off()){
			bool.setFalse();
			sys.logTurnOff();
			assert(off());
		}
	}

	public void exit() {
		if (on()){
			bool.setFalse();
			sys.exit();
			assert(off());
		}
	}
}