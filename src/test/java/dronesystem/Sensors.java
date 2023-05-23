package dronesystem;

import static ast.interp.util.Program.par;

public class Sensors {
	private final Barometer barometer = new Barometer();
	private final Wifi wifi = new Wifi();
	private final Temperature temperature = new Temperature();
	private final Battery battery = new Battery();

	public boolean staleData(){return barometer.staleData() && wifi.staleData() && battery.staleData() && temperature.staleData();}
	public boolean onLand(){return barometer.grounded();}
	public boolean returnHome(){return barometer.inAir() && (wifi.lowSNR() || (battery.returnHomeLevel() && !temperature.high())) ;}
	public boolean landImediately(){return barometer.inAir() && temperature.high();}
	public boolean carryOn(){return barometer.inAir() && !wifi.lowSNR() && !battery.returnHomeLevel() && !temperature.high();}

	public void takeReadings(){
		if (staleData()){
			par(
				()->barometer.update(),
				()->wifi.update(),
				()->temperature.update(),
				()->battery.update()
			);
			assert(onLand() ^ returnHome() ^ landImediately() ^ carryOn());
		}
	}

	public void clearReadings(){
		if (onLand() ^ returnHome() ^ landImediately() ^ carryOn()){
			barometer.reset();
			wifi.reset();
			temperature.reset();
			battery.reset();
			assert (staleData());
		}
	}

}