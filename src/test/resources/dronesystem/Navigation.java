import org.checkerframework.checker.units.qual.Temperature;

public class Navigation {

	private final RoutePlan routePlan = new RoutePlan();

	public boolean hasNextWaypoint(){return routePlan.hasNextWaypoint();}
	public boolean noMoreWaypoints(){return routePlan.noMoreWaypoints();}

	public void takeOff(){
		if (hasNextWaypoint() ^ noMoreWaypoints()){
			routePlan.land();
			assert(hasNextWaypoint() ^ noMoreWaypoints());
		}
	}

	public void land(){
		if (hasNextWaypoint() ^ noMoreWaypoints()){
			routePlan.land();
			assert(hasNextWaypoint() ^ noMoreWaypoints());
		}
	}

	public void returnHome(){
		if (hasNextWaypoint() ^ noMoreWaypoints()){
			routePlan.returnHome();
			assert(hasNextWaypoint() ^ noMoreWaypoints());
		}
	}

	public void travelToNextWaypoint(){
		if (hasNextWaypoint()){
			routePlan.travelToNextWaypoint();
			assert(hasNextWaypoint() ^ noMoreWaypoints());
		}
	}
}