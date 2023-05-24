package dronesystem;

public class Controller {

	private final RoutePlan routePlan = new RoutePlan();
	private final Sensors sensors = new Sensors();
	public boolean onLandAndOkToTravel(){return routePlan.waypointNotInsertedReady() && sensors.onLandAndOkToTravel();}
	public boolean inAirAndOkToTravel(){return routePlan.waypointNotInsertedReady() && sensors.inAirAndOkToTravel();}
	public boolean instructedToTravel(){return routePlan.waypointConsumed() && sensors.allUnkown();}
	public boolean returnHome(){return routePlan.waypointInsertedReady() && sensors.returnHome();}
	public boolean landImediately(){return routePlan.waypointInsertedReady() && sensors.landImediately();}

	public void action(){
		if (instructedToTravel()){
			sensors.writeAll();
			assert(onLandAndOkToTravel() ^ inAirAndOkToTravel() ^ returnHome() ^ landImediately());
		}
		if (onLandAndOkToTravel()){
			sensors.readAll();
			routePlan.insertTakeoff();
			routePlan.travelToNextWaypoint();
			assert(instructedToTravel());
		}
		if (inAirAndOkToTravel()){
			sensors.readAll();
			routePlan.travelToNextWaypoint();
			assert(instructedToTravel());
		}
		if (returnHome()){
			sensors.readAll();
			routePlan.insertReturnHome();
			routePlan.travelToNextWaypoint();
			assert(instructedToTravel());
		}
		if (landImediately()){
			sensors.readAll();
			routePlan.insertLanding();
			routePlan.travelToNextWaypoint();
			assert(instructedToTravel());
		}
	}

}