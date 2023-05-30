package simplethermostat;

import ast.terms.Base;

@Base
public class Logging {

	public boolean na() { return true; }

	public void logOffAndBelowTarget() {
		if (na()){
			System.out.println("OffAndBelowTarget.");
			assert(na());
		}
	}

	public void logOffAndOnOrAboveTarget() {
		if (na()){
			System.out.println("OffAndOnOrAboveTarget.");
			assert(na());
		}
	}

	public void logOnAndBelowTarget() {
		if (na()){
			System.out.println("OnAndBelowTarget.");
			assert(na());
		}
	}

	public void logOnAndOnOrAboveTarget() {
		if (na()){
			System.out.println("OnAndOnOrAboveTarget.");
			assert(na());
		}
	}
}