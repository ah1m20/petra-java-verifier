package dronesystem;

public class Sensors {
	private final Position position = new Position();
	private final Wifi wifi = new Wifi();
	private final Temperature temperature = new Temperature();
	private final Battery battery = new Battery();

	public boolean onLandAndOkToTravel(){return position.onLand() && !wifi.lowSNR() && !battery.returnHomeLevel() && !temperature.high();}

	public boolean inAirAndOkToTravel(){return !position.onLand() && !wifi.lowSNR() && !battery.returnHomeLevel() && !temperature.high();}
	public boolean returnHome(){return !position.onLand() && ((wifi.lowSNR() && !temperature.high()) || (battery.returnHomeLevel() && !temperature.high()));}

	public boolean landImediately(){return !position.onLand() && temperature.high();}


}