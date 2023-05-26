package dronesystem;

/*
 * Work towards a new model for drone, which was developed by thinking in terms of Hierachical State Machines (HSM). This seem to be a close fit to Petra whilst being wildy used and being a simple visual tool to prototype the model.
 * I believe the model is close to being verified and thinking in terms of HSMs helped this. Work should be done on seeing how to have an efficient thinking and modelling process to develop verified and validated Petra programs.
 * The problem is how to go from the something which is poorly/unspecified within the domain, to a fully specified correct system in Petra.
 * As Petra is new language / paradigm for formal verification it is difficult to map directly from ideas to verified models, without strong process for doing so.
 */

public class Controller {

	private final Position position = new Position();
	private final Flight flight = new Flight();

	private final RoutePlan routePlan = new RoutePlan();

	public boolean grounded(){return !flight.canTravel() && position.onLand() && routePlan.nextWaypointsNotLoaded();}
	public boolean flight(){return flight.canTravel() && position.inAir() && routePlan.nextWaypointsLoaded();}
	public boolean returnHome(){return flight.mustReturnHome() && !position.atHome() && routePlan.nextWaypointsLoaded();}

	public boolean arrivedAtHome(){return position.atHome() && routePlan.nextWaypointsNotLoaded();}
	public boolean mustLand(){return flight.mustLand() && !(position.atHome() || position.onLand());}

	public boolean landed(){return flight.canTravel() && position.onLand() && routePlan.nextWaypointsNotLoaded();}

	public void action(){
		if (grounded()){
			routePlan.insertTakeoff();
			routePlan.insertRoute();
			assert(flight());
		}
		if (flight()){
			;
			assert(returnHome() ^ mustLand() ^ flight());
		}
		if (returnHome()){
			routePlan.insertReturnHome();
			routePlan.travelToNextWaypoint();
			position.waitUntilHome();
			assert(arrivedAtHome());
		}
		if (mustLand()){
			routePlan.insertLanding();
			routePlan.travelToNextWaypoint();
			position.waitUntilLanded();
			assert(landed());
		}
	}

}