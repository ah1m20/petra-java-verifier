package dronesystem;
import ast.terms.Base;
@Base public class RoutePlan {
	private final DroneConnection connection = new DroneConnection();
	private final Waypoint[] route = new Waypoint[]{
			new Waypoint(10,0,0),
			new Waypoint(10,10,0),
			new Waypoint(10,10,10),
			new Waypoint(0,0,0)
	};

	private volatile int next = 0;
	private final int home = route.length-1;

	public boolean hasNextWaypoint(){return next < route.length-1;}
	public boolean noMoreWaypoints(){return next == route.length-1;}

	public void travelToNextWaypoint(){
		if (hasNextWaypoint()){
			connection.goToXYZ(route[next].getX(),route[next].getY(),route[next].getZ());
			next++;
			assert(hasNextWaypoint() ^ noMoreWaypoints());
		}
	}

	public void land(){
		if (hasNextWaypoint() ^ noMoreWaypoints()){
			connection.land();
			assert(hasNextWaypoint() ^ noMoreWaypoints());
		}
	}

	public void returnHome(){
		if (hasNextWaypoint() ^ noMoreWaypoints()){
			next = home;
			connection.goToXYZ(route[next].getX(),route[next].getY(),route[next].getZ());
			assert(noMoreWaypoints());
		}
	}


}