package dronesystem;

public class Flight {

	private final Control control = new Control();

	private final Position position = new Position();
	private final Wifi wifi = new Wifi();
	private final Temperature temperature = new Temperature();
	private final Battery battery = new Battery();

	public boolean onLandAndNotOkToTravel(){return control.on() && position.onLand() && (wifi.lowSNR() || battery.returnHomeLevel() || temperature.high());}

	public boolean onLandAndOkToTravel(){return control.on() && position.onLand() && !wifi.lowSNR() && !battery.returnHomeLevel() && !temperature.high();}

	public boolean inAirAndOkToTravel(){return control.on() && !position.onLand() && !wifi.lowSNR() && !battery.returnHomeLevel() && !temperature.high();}

	public boolean returnHome(){return control.on() && !position.onLand() && ((wifi.lowSNR() && !temperature.high()) || (battery.returnHomeLevel() && !temperature.high()));}

	public boolean atHome(){return control.off() && position.atHome();}

	public boolean landImediately(){return control.on() && !position.onLand() && temperature.high();}

	public void init() {
		if (atHome()){
			position.waitUntilInAir();
			assert (onLandAndNotOkToTravel() ^ onLandAndOkToTravel() ^ inAirAndOkToTravel() ^ returnHome() ^ landImediately());
		}
	}

	public void waitUntilInAir() {
		if (onLandAndOkToTravel() ^ onLandAndNotOkToTravel()){
			position.waitUntilInAir();
			assert (inAirAndOkToTravel() ^ returnHome() ^ landImediately());
		}
	}

	public void waitUntilOnLand() {
		if (inAirAndOkToTravel() ^ returnHome() ^ landImediately()){
			position.waitUntilLanded();
			assert (onLandAndOkToTravel() ^ onLandAndNotOkToTravel());
		}
	}

	public void waitUntilHome() {
		if (inAirAndOkToTravel() ^ returnHome() ^ landImediately()){
			position.waitUntilHome();
			control.turnOff();
			assert (atHome());
		}
	}
}