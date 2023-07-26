package com.cognitionbox.petra.examples.lightingsystem2;

import static com.cognitionbox.petra.ast.interp.util.Program.par;

public class Light implements Runnable {
	private final Power p1 = new Power();
	private final Control c1 = new Control();

	private final Power p2 = new Power();
	private final Control c2 = new Control();

	public boolean bothOn() { return p1.on() && c1.on() && p2.on() && c2.on(); }
	public boolean eitherOff() { return ( p1.off() || c1.off()) || ( p2.off() || c2.off()); }

	public void run() {
		if (eitherOff()){
			par(()-> p1.turnOn(),
					()-> c1.turnOn(),
					()-> p2.turnOn(),
					()-> c2.turnOn());
			assert(bothOn());
		} else if (bothOn()){
			par(()-> p1.turnOff(), ()-> c1.turnOff(), ()-> p2.turnOff(), ()-> c2.turnOff());
			assert(eitherOff());
		}
	}
}