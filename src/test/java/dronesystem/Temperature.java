package dronesystem;
import ast.terms.Base;
@Base public class Temperature {
	private final DroneConnection connection = new DroneConnection();
	private volatile float degrees = 0;
	private volatile boolean stale = true;

	public boolean staleData() {return stale;}
	public boolean low() { return degrees < 30; }
	public boolean normal() { return degrees >= 30 && degrees <= 70; }
	public boolean high() { return degrees > 70; }

	public void update() {
		if (staleData()) {
			degrees = connection.getTemp();
			stale = false;
			assert (low() ^ normal() ^ high());
		}
	}

	public void reset() {
		if (low() ^ normal() ^ high()) {
			stale = true;
			assert (staleData());
		}
	}


}