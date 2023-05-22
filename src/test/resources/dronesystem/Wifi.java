@Base public class Wifi {
	private final DroneConnection connection = new DroneConnection();
	private volatile float snr = 0;
	private volatile boolean stale = true;

	public boolean staleData() {return stale;}
	public boolean lowSNR() {return !stale && snr < 0.2;}
	public boolean normalSNR() {return !stale && snr >= 0.2 && snr <= 0.6;}
	public boolean highSNR() {return !stale && snr > 0.6;}

	public void update() {
		if (staleData()) {
			snr = connection.getSNR();
			stale = false;
			assert (lowSNR() ^ normalSNR() ^ highSNR());
		}
	}

	public void reset() {
		if (lowSNR() ^ normalSNR() ^ highSNR()) {
			stale = true;
			assert (staleData());
		}
	}
}