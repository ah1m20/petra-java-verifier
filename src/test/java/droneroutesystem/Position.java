package droneroutesystem;

import ast.terms.Base;

@Base
public class Position {
	private final Sys sys = new Sys();
	private final DroneConnection connection = DroneConnection.getDroneConnection();

	public boolean a(){return connection.atA();}

	public boolean b(){return connection.atB();}

	public boolean c(){return connection.atC();}

	public boolean onLand(){ return connection.onLand(); }

	public boolean atHome(){ return connection.onLand(); }

	public void travelToHome(){
		if (onLand() ^ atHome() ^ a() ^ b() ^ c()){
			sys.logTravelToHome();
			connection.goToHome();
			sys.logWaitUntilHome();
			while(!atHome()){
				sys.sleep();
			}
			assert (atHome());
		}
	}

	public void travelToLand(){
		if (onLand() ^ atHome() ^ a() ^ b() ^ c()){
			sys.logTravelToLand();
			connection.goToLand();
			sys.logWaitUntilLanded();
			while(!onLand()){
				sys.sleep();
			}
			assert (onLand());
		}
	}

	public void travelFromGroundToA(){
		if (onLand() ^ atHome() ^ a() ^ b() ^ c()){
			sys.logTravelFromGroundToA();
			connection.goToA();
			sys.logWaitUntilA();
			while(!a()){
				sys.sleep();
			}
			assert (a());
		}
	}

	public void travelFromAToB(){
		if (onLand() ^ atHome() ^ a() ^ b() ^ c()){
			sys.logTravelFromAToB();
			connection.goToB();
			sys.logWaitUntilB();
			while(!b()){
				sys.sleep();
			}
			assert (b());
		}
	}

	public void travelFromBToC(){
		if (onLand() ^ atHome() ^ a() ^ b() ^ c()){
			sys.logTravelFromBToC();
			connection.goToC();
			sys.logWaitUntilC();
			while(!c()){
				sys.sleep();
			}
			assert (c());
		}
	}
}