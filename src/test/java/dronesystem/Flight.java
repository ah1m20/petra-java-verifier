package dronesystem;

public class Flight {
	private final Sensors sensors = new Sensors();

	public boolean canTravel(){return !(sensors.landImediately() || sensors.returnHome());}

	public boolean mustLand(){return sensors.landImediately();}

	public boolean mustReturnHome(){return sensors.returnHome();}

	public boolean mustHold(){return !(sensors.landImediately() || sensors.returnHome());}

}