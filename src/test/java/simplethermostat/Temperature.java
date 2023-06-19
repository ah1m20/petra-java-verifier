package simplethermostat;

import ast.terms.Base;

@Base public class Temperature {
	private final Sensor sensor = Sensor.getInstance();

	public boolean aboveOrEqualToTarget() { return sensor.aboveOrEqualToTarget(); }
	public boolean belowTarget() { return sensor.belowTarget(); }

}