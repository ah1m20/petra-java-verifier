package droneroutesystem;

import ast.terms.Base;

@Base
public class Position {
	private final DroneConnection connection = DroneConnection.getDroneConnection();

	public boolean a(){return connection.atA();}

	public boolean b(){return connection.atB();}

	public boolean c(){return connection.atC();}

	public boolean onLand(){ return connection.onLand(); }

	public boolean atHome(){ return connection.atHome(); }

	public void travelToHome(){
		if (onLand() ^ atHome() ^ a() ^ b() ^ c()){
			connection.goToHome();
			connection.waitUntilHome();
			assert (atHome());
		}
	}

	public void travelToLand(){
		if (onLand() ^ atHome() ^ a() ^ b() ^ c()){
			connection.goToLand();
			connection.waitUntilLanded();
			assert (onLand());
		}
	}

	public void travelFromGroundToA(){
		if (onLand() ^ atHome() ^ a() ^ b() ^ c()){
			connection.goToA();
			connection.waitUntilA();
			assert (a());
		}
	}

	public void travelFromAToB(){
		if (onLand() ^ atHome() ^ a() ^ b() ^ c()){
			connection.goToB();
			connection.waitUntilB();
			assert (b());
		}
	}

	public void travelFromBToC(){
		if (onLand() ^ atHome() ^ a() ^ b() ^ c()){
			connection.goToC();
			connection.waitUntilC();
			assert (c());
		}
	}
}