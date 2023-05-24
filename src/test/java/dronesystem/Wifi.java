package dronesystem;

import ast.terms.Base;

@Base
public class Wifi {
	private final DroneConnection connection = new DroneConnection();


	public boolean lowSNR() {return !unknown && connection.getSNR() < 0.2;}
	public boolean normalSNR() {return !unknown && connection.getSNR() >= 0.2 && connection.getSNR() <= 0.6;}
	public boolean highSNR() {return !unknown && connection.getSNR() > 0.6;}

	private volatile boolean unknown = false;
	public boolean unknown(){return unknown;}

	public void read(){
		if (lowSNR() ^ normalSNR() ^ highSNR()){
			unknown = true;
			assert (unknown());
		}
	}

	public void write(){
		if (unknown()){
			unknown = false;
			assert (lowSNR() ^ normalSNR() ^ highSNR());
		}
	}
}