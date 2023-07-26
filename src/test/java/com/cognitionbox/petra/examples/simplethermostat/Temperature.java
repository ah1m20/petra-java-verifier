package com.cognitionbox.petra.examples.simplethermostat;

import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.Initial;

@Base public class Temperature {
	private final Sensor sensor = Sensor.getInstance();

	public boolean aboveOrEqualToTarget() { return sensor.aboveOrEqualToTarget(); }
	@Initial
	public boolean belowTarget() { return sensor.belowTarget(); }

}