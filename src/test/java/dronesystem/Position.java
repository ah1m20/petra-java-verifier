package dronesystem;

import ast.terms.Base;

@Base
public class Position {
	private final DroneConnection connection = new DroneConnection();
	public boolean onLand(){ return !unknown && connection.getZ() == 0; }
	public boolean inAir() {
		return !unknown && connection.getZ() > 0;
	}

	private volatile boolean unknown = false;
	public boolean unknown(){return unknown;}

	public void read(){
		if (onLand() ^ inAir()){
			unknown = true;
			assert (unknown());
		}
	}

	public void write(){
		if (unknown()){
			unknown = false;
			assert (onLand() ^ inAir());
		}
	}
}