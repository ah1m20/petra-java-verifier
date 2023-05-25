package dronesystem;

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