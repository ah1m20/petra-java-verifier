package dronesystem;
import ast.terms.Base;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Base public class RoutePlan {
	private final DroneConnection connection = new DroneConnection();

	private final Waypoint home = new Waypoint(0,0,0);

	private final List<Waypoint> route = new LinkedList<>();

	private volatile int lastWaypoints = 0;

	private volatile int waypointIndex = 0;

	public boolean waypointsInserted(){return arrived() && (route.size() - lastWaypoints)>0;}
	public boolean noWaypointsInserted(){return travelling() && (route.size() - lastWaypoints)==0;}

	public boolean travelling(){
		return route.get(waypointIndex).getX()!=connection.getX() &&
				route.get(waypointIndex).getY()!=connection.getY() &&
				route.get(waypointIndex).getZ()!=connection.getZ();
	}

	public boolean arrived(){
		return route.get(waypointIndex).getX()==connection.getX() &&
				route.get(waypointIndex).getY()==connection.getY() &&
				route.get(waypointIndex).getZ()==connection.getZ();
	}

	public void travel(){
		if (arrived()){
			connection.goToXYZ(route.get(waypointIndex).getX(),route.get(waypointIndex).getY(),route.get(waypointIndex).getZ());
			waypointIndex++;
			assert(travelling());
		}
	}

	public void waitForArrival(){
		if (travelling()){
			while(!arrived()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert(arrived());
		}
	}

	public void insertLanding(){
		if (noWaypointsInserted()){
			int x = connection.getX();
			int y = connection.getY();
			lastWaypoints = route.size();
			route.add(waypointIndex,new Waypoint(x,y,0));
			assert(waypointsInserted());
		}
	}

	public void insertTakeoff(){
		if (noWaypointsInserted() ^ waypointsInserted()){
			int x = connection.getX();
			int y = connection.getY();
			lastWaypoints = route.size();
			route.add(waypointIndex,new Waypoint(x,y,100));
			assert(waypointsInserted());
		}
	}

	public void insertReturnHome(){
		if (noWaypointsInserted() ^ waypointsInserted()){;
			lastWaypoints = route.size();
			route.add(waypointIndex,home);
			assert(waypointsInserted());
		}
	}

	public void insertRoute(){
		if (noWaypointsInserted() ^ waypointsInserted()){
			lastWaypoints = route.size();
			route.addAll(Arrays.asList(new Waypoint(10,0,0),
					new Waypoint(10,10,0),
					new Waypoint(10,10,10),home));
			assert(waypointsInserted());
		}
	}


}