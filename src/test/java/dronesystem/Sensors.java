package dronesystem;

public class Sensors {
	private final Position position = new Position();
	private final Wifi wifi = new Wifi();
	private final Temperature temperature = new Temperature();
	private final Battery battery = new Battery();

	public boolean allUnkown(){return position.unknown() && wifi.unknown() && temperature.unknown() && battery.unknown();}

	public boolean onLandAndOkToTravel(){return position.onLand() && !wifi.lowSNR() && !battery.returnHomeLevel() && !temperature.high();}

	public boolean inAirAndOkToTravel(){return !position.onLand() && !wifi.lowSNR() && !battery.returnHomeLevel() && !temperature.high();}
	public boolean returnHome(){return !position.onLand() && ((wifi.lowSNR() && !temperature.high()) || (battery.returnHomeLevel() && !temperature.high()));}

	public boolean landImediately(){return !position.onLand() && temperature.high();}


	public void writeAll() {
		if (allUnkown()){
			position.write();
			wifi.write();
			temperature.write();
			battery.write();
			assert (onLandAndOkToTravel() ^ inAirAndOkToTravel() ^ returnHome() ^ landImediately());
		}
	}

	public void readAll() {
		if (onLandAndOkToTravel() ^ inAirAndOkToTravel() ^ returnHome() ^ landImediately()){
			position.read();
			wifi.read();
			temperature.read();
			battery.read();
			assert (allUnkown());
		}
	}
}