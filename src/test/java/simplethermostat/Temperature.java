package simplethermostat;

import ast.terms.Base;
import ast.terms.Initial;

@Base public class Temperature {
	private final Sensor sensor = Sensor.getInstance();

	public boolean aboveOrEqualToTarget() { return sensor.aboveOrEqualToTarget(); }
	@Initial
	public boolean belowTarget() { return sensor.belowTarget(); }

}