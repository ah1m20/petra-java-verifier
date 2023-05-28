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

	public boolean flight(){return flight.inAirAndOkToTravel() && (routePlan.home() || routePlan.takeOff() || routePlan.c() || routePlan.d() || routePlan.ground());}
	public boolean returnHome(){return flight.returnHome() && routePlan.home();}
	public boolean atHome(){return flight.atHome() && (routePlan.home() || routePlan.takeOff() || routePlan.c() || routePlan.d() || routePlan.ground());}
	public boolean mustLand(){return flight.landImediately() && routePlan.ground();}

	public boolean landed(){return flight.onLandAndOkToTravel() && (routePlan.home() || routePlan.takeOff() || routePlan.c() || routePlan.d() || routePlan.ground());}

	public void action(){
		if (atHome()){
			flight.init();
			routePlan.takeoff();
			flight.waitUntilInAir();
			assert(flight() ^ returnHome() ^ mustLand());
		}
		if (flight()){
			routePlan.travel();
			assert(returnHome() ^ mustLand() ^ flight());
		}
		if (returnHome()){
			routePlan.returnToHome();
			flight.waitUntilHome();
			assert(atHome());
		}
		if (mustLand()){
			routePlan.land();
			flight.waitUntilOnLand();
			assert(landed());
		}
		if (landed()){
			routePlan.land();
			flight.waitUntilInAir();
			assert(flight());
		}
	}

}