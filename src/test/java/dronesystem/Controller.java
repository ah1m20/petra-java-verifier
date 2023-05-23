package dronesystem;

public class Controller {

	private final Navigation navigation = new Navigation();
	private final Sensors sensors = new Sensors();

	public boolean staleSensorData(){return sensors.staleData();}
	public boolean onLandAndNotOkToTravel(){return !sensors.carryOn();}
	public boolean onLandAndOkToTravel(){return sensors.carryOn();}
	public boolean okToTravel(){return sensors.carryOn();}
	public boolean returnHome(){return sensors.returnHome();}
	public boolean landImediately(){return sensors.landImediately();}

	public void action(){
		if (staleSensorData()){
			sensors.takeReadings();
			assert(onLandAndNotOkToTravel() ^ onLandAndOkToTravel() ^ okToTravel() ^ returnHome() ^ landImediately());
		}
		if (onLandAndNotOkToTravel()){
			sensors.clearReadings();
			assert(staleSensorData());
		}
		if (onLandAndOkToTravel()){
			navigation.takeOff();
			sensors.clearReadings();
			assert(staleSensorData());
		}
		if (okToTravel()){
			navigation.travelToNextWaypoint();
			sensors.clearReadings();
			assert(staleSensorData());
		}
		if (returnHome()){
			navigation.returnHome();
			sensors.clearReadings();
			assert(staleSensorData());
		}
		if (landImediately()){
			navigation.land();
			sensors.clearReadings();
			assert(staleSensorData());
		}
	}

}