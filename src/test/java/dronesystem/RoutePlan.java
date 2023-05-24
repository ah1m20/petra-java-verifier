package dronesystem;
import ast.terms.Base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Base public class RoutePlan {
	private final DroneConnection connection = new DroneConnection();

	private final Waypoint home = new Waypoint(0,0,0);

	private final List<Waypoint> route = new ArrayList(Arrays.asList(
			new Waypoint(10,0,0),
			new Waypoint(10,10,0),
			new Waypoint(10,10,10),
			home));

	private volatile int waypointIndex = 0;
	private volatile int lastWaypointIndex = 0;

	private int waypoints = route.size();

	public boolean waypointInsertedReady(){return (route.size() - waypoints)==1 && waypointIndex < route.size()-1;}

	public boolean waypointNotInsertedReady(){return (route.size() - waypoints)==0 && waypointIndex < route.size()-1;}

	public boolean waypointConsumed(){return (waypointIndex - lastWaypointIndex)==1;}

	public void travelToNextWaypoint(){
		if (waypointInsertedReady() ^ waypointNotInsertedReady()){
			connection.goToXYZ(route.get(waypointIndex).getX(),route.get(waypointIndex).getY(),route.get(waypointIndex).getZ());
			lastWaypointIndex = waypointIndex;
			waypointIndex++;
			assert(waypointConsumed());
		}
	}

	public void insertLanding(){
		if (waypointNotInsertedReady()){
			int x = connection.getX();
			int y = connection.getY();
			waypoints = route.size();
			route.add(waypointIndex,new Waypoint(x,y,0));
			assert(waypointInsertedReady());
		}
	}

	public void insertTakeoff(){
		if (waypointNotInsertedReady()){
			int x = connection.getX();
			int y = connection.getY();
			waypoints = route.size();
			route.add(waypointIndex,new Waypoint(x,y,100));
			assert(waypointInsertedReady());
		}
	}

	public void insertReturnHome(){
		if (waypointNotInsertedReady()){;
			waypoints = route.size();
			route.add(waypointIndex,home);
			assert(waypointInsertedReady());
		}
	}


}