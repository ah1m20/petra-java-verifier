package dronesystem;
import ast.terms.Base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Base public class RoutePlan {
	private final DroneConnection connection = new DroneConnection();

	private final Waypoint home = new Waypoint(0,0,0);

	private final List<Waypoint> route = new ArrayList();

	private volatile int waypointIndex = 0;
	private volatile int lastWaypointIndex = 0;

	public boolean nextWaypointsLoaded(){return (waypointIndex - lastWaypointIndex)>1;}
	public boolean nextWaypointsNotLoaded(){return (waypointIndex - lastWaypointIndex)==0;}

	public void travelToNextWaypoint(){
		if (nextWaypointsLoaded()){
			connection.goToXYZ(route.get(waypointIndex).getX(),route.get(waypointIndex).getY(),route.get(waypointIndex).getZ());
			lastWaypointIndex = waypointIndex;
			waypointIndex++;
			assert(nextWaypointsNotLoaded());
		}
	}

	public void insertLanding(){
		if (nextWaypointsNotLoaded()){
			int x = connection.getX();
			int y = connection.getY();
			route.add(waypointIndex,new Waypoint(x,y,0));
			assert(nextWaypointsLoaded());
		}
	}

	public void insertTakeoff(){
		if (nextWaypointsNotLoaded()){
			int x = connection.getX();
			int y = connection.getY();
			route.add(waypointIndex,new Waypoint(x,y,100));
			assert(nextWaypointsLoaded());
		}
	}

	public void insertReturnHome(){
		if (nextWaypointsNotLoaded()){;
			route.add(waypointIndex,home);
			assert(nextWaypointsLoaded());
		}
	}

	public void insertRoute(){
		if (nextWaypointsNotLoaded()){
			route.addAll(Arrays.asList(new Waypoint(10,0,0),
					new Waypoint(10,10,0),
					new Waypoint(10,10,10),home));
			assert(nextWaypointsLoaded());
		}
	}


}