package droneroutesystem;
import ast.terms.Base;

public class RoutePlan {

	private final Position position = new Position();

	public boolean atHome(){return position.atHome();}
	public boolean takeOff(){return position.takeOff();}
	public boolean a(){return position.a();}

	public boolean b(){return position.b();}

	public boolean c(){return position.c();}

	public boolean ground(){return position.ground();}

	public boolean onLand(){return position.onLand();}

	public boolean inAir(){return position.inAir();}

	public boolean other(){return position.other();}

	public void travel(){
		if (ground()){
			position.travelFromGroundToA();
			position.waitUntilA();
			assert(a());
		}
		if (a()){
			position.travelFromAToB();
			position.waitUntilB();
			assert(b());
		}
		if (b()){
			position.travelFromBToC();
			position.waitUntilC();
			assert(c());
		}
	}

	public void land(){
		if (takeOff() ^ a() ^ b() ^ c() ^ other()){
			position.travelToLand();
			position.waitUntilLanded();
			assert(ground());
		}
	}

	public void returnToHome(){
		if (takeOff() ^ a() ^ b() ^ c() ^ other()){
			position.travelToHome();
			position.waitUntilHome();
			assert(atHome());
		}
	}

}