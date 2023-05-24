package dronesystem;

import ast.terms.Base;

@Base
public class Movement {
	private final DroneConnection connection = new DroneConnection();
	public boolean inAirAndStationary() {
		return connection.getZ() > 0 &&
			connection.getVelocityX()==0 &&
			connection.getVelocityY()==0 &&
			connection.getVelocityZ()==0;
	}

	public boolean takingOff() {
		return connection.getVelocityX()==0 &&
				connection.getVelocityY()==0 &&
				connection.getVelocityZ()>0;
	}

	public boolean landing() {
		return connection.getVelocityX()==0 &&
				connection.getVelocityY()==0 &&
				connection.getVelocityZ()<0;
	}

	public boolean inAirAndMoving() {
		return connection.getZ() > 0 &&
				(connection.getVelocityX() > 0 ||
				connection.getVelocityY() > 0 ||
				connection.getVelocityZ() > 0);
	}
}