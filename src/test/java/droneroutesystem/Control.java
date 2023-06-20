package droneroutesystem;

import ast.terms.Base;
import ast.terms.Initial;

@Base
public class Control {
	private final Sys sys = new Sys();
	private final Bool active = new Bool(true);

	@Initial
	public boolean on() { return active.isTrue(); }

	public boolean off() { return active.isFalse(); }

	public void turnOn() {
		if (on() ^ off()){
			active.setTrue();
			sys.logTurnOn();
			assert(on());
		}
	}

	public void turnOff() {
		if (on()){
			active.setFalse();
			sys.logTurnOff();
			assert(off());
		}
	}

	public void exit() {
		if (off()){
			sys.exit();
			assert(off());
		}
	}
}