package dronesystem;
import ast.terms.Base;

@Base public class RoutePlan {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	private final Waypoint home = new Waypoint(0,0,0);
	private final Waypoint takeOff = new Waypoint(0,0,100);
	private final Waypoint a = new Waypoint(10,0,100);
	private final Waypoint b = new Waypoint(10,10,100);
	private final Waypoint c = new Waypoint(20,20,100);
	private final Waypoint d = new Waypoint(40,40,100);
	private final Waypoint e = new Waypoint(80,40,100);
	private boolean grounded = false;
	private boolean ground = true;
	private volatile Waypoint heading=takeOff;

	public boolean home(){return this.grounded && this.ground && this.heading==home;}
	public boolean takeOff(){return !grounded && !this.ground && this.heading==takeOff;}
	public boolean a(){return !grounded && !this.ground && this.heading==a;}

	public boolean b(){return !grounded && !this.ground && this.heading==b;}

	public boolean c(){return !grounded && !this.ground && this.heading==c;}

	public boolean d(){return !grounded && !this.ground && this.heading==d;}

	public boolean e(){return !grounded && !this.ground && this.heading==e;}

	public boolean ground(){return !grounded && this.ground;}

	public void travel(){
		if (ground()){
			System.out.println("land");
			heading = new Waypoint(connection.getX(),connection.getY(),0);
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(a());
		}
		if (a()){
			System.out.println("a");
			heading = b;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(b());
		}
		if (b()){
			System.out.println("b");
			heading = c;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(c());
		}
		if (c()){
			System.out.println("c");
			heading = d;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(d());
		}
		if (d()){
			System.out.println("d");
			heading = e;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(e());
		}
		if (e()){
			System.out.println("e");
			ground = true;
			grounded = true;
			heading = home;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(home());
		}
	}

	public void land(){
		if (takeOff() ^ c() ^ d()){
			System.out.println("land");
			heading = new Waypoint(connection.getX(),connection.getY(),0);
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(ground());
		}
	}

	public void returnToHome(){
		if (takeOff() ^ c() ^ d()){
			System.out.println("returnToHome");
			heading = home;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(ground());
		}
	}

}