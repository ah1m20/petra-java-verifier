package com.cognitionbox.petra.examples.duallightingsystem;

import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.examples.simplelightsystem.Bool;

@Base
public class Control {
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