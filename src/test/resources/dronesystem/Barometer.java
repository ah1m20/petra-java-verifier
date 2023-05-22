@Base public class Barometer {
	private final DroneConnection connection = new DroneConnection();
	private volatile float altitude = 0;
	private volatile boolean stale = true;
	public boolean onLand(){ return altitude = 0; }
	public boolean inAir() { return altitude > 0; }

		public void update(){
			if (staleData()){
				altitude = connection.getAltitude();
				stale = false;
				assert (onLand() ^ inAir());
			}
		}

		public void reset(){
			if  (onLand() ^ inAir()){
				stale = true;
				assert (staleData());
			}
		}
}