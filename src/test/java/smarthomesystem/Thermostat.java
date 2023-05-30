package smarthomesystem;

import ast.terms.Base;

@Base public class Thermostat {

	private final Temperature temperature = new Temperature();
	private volatile boolean updated = false;

	private volatile boolean control = false;

//	public boolean onAndTargetUpdated() { return temperature.getCurrent() < target && updated; }
//	public boolean offAndTargetUpdated() { return temperature.getCurrent() >= target && updated; }
//
//	public boolean onAndTargetNotUpdated() { return temperature.getCurrent() < target && !updated; }
//	public boolean offAndTargetNotUpdated() { return temperature.getCurrent() >= target && !updated; }
//
//	public void readTarget() {
//		if (off()){
//			target = temperature.getTarget();
//			assert(on());
//		}
//	}
}