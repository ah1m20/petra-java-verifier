@Base public class Battery {
	private final DroneConnection connection = new DroneConnection();
	private volatile float life = 0;
	private volatile boolean stale = true;
	public boolean staleData() { return stale; }
	public boolean returnHomeLevel() { return !stale && life < 50; }
	public boolean okLevel() { return !stale && life >= 50; }

	public void update(){
		if (staleData()){
			life = connection.getPower();
			stale = false;
			assert (returnHomeLevel() ^ okLevel());
		}
	}

	public void reset(){
		if  (returnHomeLevel() ^ okLevel()){
			stale = true;
			assert (staleData());
		}
	}
}