package dronesystem;

import ast.terms.Base;

@Base
public class Battery {
	private final DroneConnection connection = new DroneConnection();
	public boolean returnHomeLevel() { return !unknown && connection.getPower() < 50; }
	public boolean okLevel() { return !unknown && connection.getPower() >= 50; }

	private volatile boolean unknown = false;
	public boolean unknown(){return unknown;}

	public void read(){
		if (okLevel() ^ returnHomeLevel()){
			unknown = true;
			assert (unknown());
		}
	}

	public void write(){
		if (unknown()){
			unknown = false;
			assert (okLevel() ^ returnHomeLevel());
		}
	}
}