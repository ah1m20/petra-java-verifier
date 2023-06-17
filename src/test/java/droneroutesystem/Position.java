package droneroutesystem;

import ast.terms.Base;

@Base
public class Position {
	private final DroneConnection connection = DroneConnection.getDroneConnection();
	private final Waypoint home = new Waypoint(0,0,0);
	private final Waypoint takeOff = new Waypoint(0,0,100);
	private final Waypoint a = new Waypoint(10,0,100);
	private final Waypoint b = new Waypoint(10,10,100);
	private final Waypoint c = new Waypoint(20,20,100);
	private boolean grounded = false;
	private boolean ground = true;
	private volatile Waypoint heading=takeOff;

	public boolean takeOff(){return !grounded && !this.ground && this.heading==takeOff;}
	public boolean a(){return !grounded && !this.ground && this.heading==a;}

	public boolean b(){return !grounded && !this.ground && this.heading==b;}

	public boolean c(){return !grounded && !this.ground && this.heading==c;}

	public boolean ground(){return !grounded && this.ground;}

	public boolean onLand(){ return connection.getX() != 0 && connection.getY() != 0 && connection.getZ() == 0; }

	public boolean inAir(){ return connection.getZ() > 0; }

	public boolean atHome(){ return connection.getX() == 0 && connection.getY() == 0 && connection.getZ() == 0; }

	public boolean other(){ return connection.getX() == 0 && connection.getY() == 0 && connection.getZ() == 0; }

	public void waitUntilHome(){
		if (atHome() ^ takeOff() ^ a() ^ b() ^ c() ^ other()){
			System.out.println("waitUntilHome");
			while(!atHome()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert (atHome());
		}
	}

	public void waitUntilLanded(){
		if (atHome() ^ takeOff() ^ a() ^ b() ^ c() ^ other()){
			System.out.println("waitUntilLanded");
			while(!onLand()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert (ground());
		}
	}

	public void waitUntilTakenOff(){
		if (onLand()){
			System.out.println("waitUntilTakenOff");
			while(!takeOff()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert (takeOff());
		}
	}

	public void waitUntilA(){
		if (atHome() ^ takeOff() ^ a() ^ b() ^ c() ^ other()){
			System.out.println("waitUntilA");
			while(!a()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert (a());
		}
	}

	public void waitUntilB(){
		if (atHome() ^ takeOff() ^ a() ^ b() ^ c() ^ other()){
			System.out.println("waitUntilB");
			while(!b()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert (b());
		}
	}

	public void waitUntilC(){
		if (atHome() ^ takeOff() ^ a() ^ b() ^ c() ^ other()){
			System.out.println("waitUntilC");
			while(!c()){
				try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
			}
			assert (c());
		}
	}

	public void travelToHome() {
		if (atHome() ^ takeOff() ^ a() ^ b() ^ c() ^ other()){
			System.out.println("travelToHome");
			heading = home;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(atHome() ^ takeOff() ^ a() ^ b() ^ c() ^ other());
		}
	}

	public void travelFromGroundToTakeOff() {
		if (ground()){
			System.out.println("travelFromGroundToTakeOff");
			heading = takeOff;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(atHome() ^ takeOff() ^ a() ^ b() ^ c() ^ other());
		}
	}

	public void travelFromGroundToA() {
		if (ground()){
			System.out.println("travelFromGroundToA");
			heading = a;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(atHome() ^ takeOff() ^ a() ^ b() ^ c() ^ other());
		}
	}

	public void travelFromAToB() {
		if (a()){
			System.out.println("travelFromAToB");
			heading = b;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(atHome() ^ takeOff() ^ a() ^ b() ^ c() ^ other());
		}
	}

	public void travelFromBToC() {
		if (b()){
			System.out.println("travelFromBToC");
			heading = c;
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(atHome() ^ takeOff() ^ a() ^ b() ^ c() ^ other());
		}
	}

	public void travelToLand() {
		if (atHome() ^ takeOff() ^ a() ^ b() ^ c() ^ other()){
			System.out.println("travelToLand");
			heading = new Waypoint(connection.getX(),connection.getY(),0);
			connection.goToXYZ(heading.getX(), heading.getY(), heading.getZ());
			assert(atHome() ^ takeOff() ^ a() ^ b() ^ c() ^ other());
		}
	}
}