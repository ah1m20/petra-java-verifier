package com.cognitionbox.petra.examples.droneroutesystem;

public class RoutePlan {

	private final Position position = new Position();

	public boolean atHome(){return position.atHome();}

	public boolean a(){return position.a();}

	public boolean b(){return position.b();}

	public boolean c(){return position.c();}

	public boolean ground(){return position.onLand();}

	public void travel(){
		if (atHome() ^ ground()){
			position.travelFromGroundToA();
			assert(a());
		} else if (a()){
			position.travelFromAToB();
			assert(b());
		} else if (b()){
			position.travelFromBToC();
			assert(c());
		} else if (c()){
			position.travelToHome();
			assert(atHome());
		}
	}

	public void land(){
		if (atHome() ^ ground() ^ a() ^ b() ^ c()){
			position.travelToLand();
			assert(ground());
		}
	}

	public void returnToHome(){
		if (atHome() ^ ground() ^ a() ^ b() ^ c()){
			position.travelToHome();
			assert(atHome());
		}
	}

}