package dronesystem;
import ast.terms.Base;
@Base public class Temperature {
	private final DroneConnection connection = new DroneConnection();
	public boolean low() { return !unknown && connection.getTemp() < 30; }
	public boolean normal() { return !unknown && connection.getTemp() >= 30 && connection.getTemp() <= 70; }
	public boolean high() { return !unknown && connection.getTemp() > 70; }

	private volatile boolean unknown = false;
	public boolean unknown(){return unknown;}

	public void read(){
		if (low() ^ normal() ^ high()){
			unknown = true;
			assert (unknown());
		}
	}

	public void write(){
		if (unknown()){
			unknown = false;
			assert (low() ^ normal() ^ high());
		}
	}
}