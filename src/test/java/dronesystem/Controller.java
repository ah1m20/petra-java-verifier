package dronesystem;

public class Controller {

	private final Navigation navigation = new Navigation();
	private final Diagnostics diagnostics = new Diagnostics();

	public boolean staleSensorData(){return diagnostics.staleData();}
	public boolean onLandAndNotOkToTravel(){return !diagnostics.carryOn();}
	public boolean onLandAndOkToTravel(){return diagnostics.carryOn();}
	public boolean okToTravel(){return diagnostics.carryOn();}
	public boolean returnHome(){return diagnostics.returnHome();}
	public boolean landImediately(){return diagnostics.landImediately();}

	public void action(){
		if (staleSensorData()){
			diagnostics.update();
			assert(onLandAndNotOkToTravel() ^ onLandAndOkToTravel() ^ okToTravel() ^ returnHome() ^ landImediately());
		}
		if (onLandAndNotOkToTravel()){
			diagnostics.update();
			assert(onLandAndNotOkToTravel() ^ onLandAndOkToTravel());
		}
		if (onLandAndOkToTravel()){
			navigation.takeOff();
			assert(okToTravel() ^ returnHome() ^ landImediately());
		}
		if (okToTravel()){
			navigation.travelToNextWaypoint();
			assert(staleSensorData());
		}
		if (returnHome()){
			navigation.returnHome();
			assert(okToTravel() ^ landImediately());
		}
		if (landImediately()){
			navigation.land();
			assert(onLandAndNotOkToTravel() ^ onLandAndOkToTravel());
		}
	}

}