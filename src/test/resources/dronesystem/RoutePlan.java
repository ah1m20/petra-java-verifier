import org.checkerframework.checker.units.qual.Temperature;

import java.util.Arrays;

@Base public class RoutePlan {
	private final DroneConnection connection = new DroneConnection();
	private final Waypoint[] route = new Waypoint[100];
	private volatile int next = 0;
	private volatile int home = 99;

	public boolean hasNextWaypoint(){return waypoint < route.length-1;}
	public boolean noMoreWaypoints(){return waypoint = route.length-1;}

	public void travelToNextWaypoint(){
		if (hasNextWaypoint()){
			connection.goToXYZ(route[waypoint]);
			waypoint++;
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