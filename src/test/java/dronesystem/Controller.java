package dronesystem;

/*
 * Work towards a new model for drone, which was developed by thinking in terms of Hierachical State Machines (HSM). This seem to be a close fit to Petra whilst being wildy used and being a simple visual tool to prototype the model.
 * I believe the model is close to being verified and thinking in terms of HSMs helped this. Work should be done on seeing how to have an efficient thinking and modelling process to develop verified and validated Petra programs.
 * The problem is how to go from the something which is poorly/unspecified within the domain, to a fully specified correct system in Petra.
 * As Petra is new language / paradigm for formal verification it is difficult to map directly from ideas to verified models, without strong process for doing so.
 */

public class Controller {
	private final Flight flight = new Flight();

	private final RoutePlan routePlan = new RoutePlan();

	public boolean flight(){return flight.inAirAndOkToTravel() && routePlan.waypointsInserted();}
	public boolean returnHome(){return flight.returnHome() && routePlan.noWaypointsInserted();}
	public boolean atHome(){return flight.atHome() && routePlan.noWaypointsInserted();}
	public boolean mustLand(){return flight.landImediately() && routePlan.waypointsInserted();}

	public boolean landed(){return flight.onLandAndOkToTravel() && routePlan.noWaypointsInserted();}

	public void action(){
		if (atHome()){
			flight.init();
			routePlan.insertTakeoff();
			routePlan.insertRoute();
			flight.waitUntilInAir();
			assert(flight());
		}
		if (flight()){
			routePlan.travel();
			assert(returnHome() ^ mustLand() ^ flight());
		}
		if (returnHome()){
			routePlan.insertReturnHome();
			routePlan.travel();
			flight.waitUntilHome();
			assert(atHome());
		}
		if (mustLand()){
			routePlan.insertLanding();
			routePlan.travel();
			flight.waitUntilOnLand();
			assert(landed());
		}
		if (landed()){
			routePlan.insertTakeoff();
			flight.waitUntilInAir();
			assert(flight());
		}
	}

}