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

	public boolean grounded(){return !flight.canTravel() && position.onLand();}
	public boolean takenOff(){return position.inAir();}
	public boolean flight(){return flight.canTravel() || flight.mustHold();}
	public boolean returnHome(){return flight.mustReturnHome();}

	public boolean arrivedAtHome(){return position.atHome();}
	public boolean mustLand(){return flight.mustLand();}

	public boolean landed(){return position.onLand();}

	public void action(){
		if (grounded()){
			routePlan.insertTakeoff();
			assert(takenOff());
		}
		if (takenOff()){
			routePlan.insertRoute();
			assert(flight());
		}
		if (flight()){
			;
			assert(returnHome() ^ mustLand() ^ flight());
		}
		if (returnHome()){
			routePlan.insertReturnHome();
			position.waitUntilHome();
			assert(arrivedAtHome());
		}
		if (mustLand()){
			routePlan.insertLanding();
			position.waitUntilLanded();
			assert(landed());
		}
	}

}