package dronesystem;

public class Controller {

	private final RoutePlan routePlan = new RoutePlan();
	private final Sensors sensors = new Sensors();
	public boolean onLandAndOkToTravel(){return routePlan.waypointNotInsertedReady() && sensors.onLandAndOkToTravel();}
	public boolean inAirAndOkToTravel(){return routePlan.waypointNotInsertedReady() && sensors.inAirAndOkToTravel();}
	public boolean instructedToTravel(){return routePlan.waypointConsumed();}
	public boolean returnHome(){return routePlan.waypointInsertedReady() && sensors.returnHome();}
	public boolean landImediately(){return routePlan.waypointInsertedReady() && sensors.landImediately();}

	public void action(){
		if (instructedToTravel()){
			;
			assert(onLandAndOkToTravel() ^ inAirAndOkToTravel());
		}
		if (onLandAndOkToTravel()){
			routePlan.insertTakeoff();
			routePlan.travelToNextWaypoint();
			assert(instructedToTravel());
		}
		if (inAirAndOkToTravel()){
			routePlan.travelToNextWaypoint();
			assert(instructedToTravel());
		}
		if (returnHome()){
			routePlan.insertReturnHome();
			routePlan.travelToNextWaypoint();
			assert(instructedToTravel());
		}
		if (landImediately()){
			routePlan.insertLanding();
			routePlan.travelToNextWaypoint();
			assert(instructedToTravel());
		}
	}

}