package dronesystem;
import ast.terms.Base;

@Base public class RoutePlan {
	private final DroneConnection connection = new DroneConnection();

	private final Waypoint home = new Waypoint(0,0,0);

	private final Waypoint takeOff = new Waypoint(0,0,100);
	private final Waypoint c = new Waypoint(10,0,100);
	private final Waypoint d = new Waypoint(10,10,100);

	private boolean ground = false;
	private volatile Waypoint heading=home;

	public boolean home(){return !this.ground && this.heading==home;}
	public boolean takeOff(){return !this.ground && this.heading==takeOff;}
	public boolean c(){return !this.ground && this.heading==c;}

	public boolean d(){return !this.ground && this.heading==d;}

	public boolean ground(){return this.ground;}

	public void takeoff(){
		if (home()){
			ground = false;
			heading = takeOff;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(takeOff());
		}
	}

	public void travel(){
		if (takeOff()){
			ground = false;
			heading = c;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(c());
		}
		if (c()){
			ground = false;
			heading = d;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(d());
		}
	}

	public void land(){
		if (home() ^ takeOff() ^ c() ^ d()){
			ground = true;
			heading = new Waypoint(connection.getX(),connection.getY(),0);
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(ground());
		}
	}

	public void returnToHome(){
		if (home() ^ takeOff() ^ c() ^ d()){
			heading = home;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(ground());
		}
	}

}