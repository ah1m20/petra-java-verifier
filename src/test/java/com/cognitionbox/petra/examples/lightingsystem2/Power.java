package com.cognitionbox.petra.examples.lightingsystem2;

import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.examples.lightsystem.Bool;

@Base
public class Power {
	private final com.cognitionbox.petra.examples.lightsystem.Bool bool = new Bool();
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