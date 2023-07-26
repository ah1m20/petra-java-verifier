package com.cognitionbox.petra.examples.flatlightingsystem;

import com.cognitionbox.petra.ast.terms.Base;

@Base public class Light {
	private final Bool power = new Bool();
	private final Bool control = new Bool();

	public boolean on() { return power.isTrue() && control.isFalse(); }
	public boolean off() { return power.isFalse() || control.isFalse(); }

	public void turnOn() {
		if (on() ^ off()){
			power.setTrue();
			power.setTrue();
			assert(on());
		}
	}

	public void turnOff() {
		if (on() ^ off()){
			power.setFalse();
			power.setFalse();
			assert(off());
		}
	}
}