package dronesystem;

/*
 * Work towards a new model for drone, which was developed by thinking in terms of Hierachical State Machines (HSM). This seem to be a close fit to Petra whilst being wildy used and being a simple visual tool to prototype the model.
 * I believe the model is close to being verified and thinking in terms of HSMs helped this. Work should be done on seeing how to have an efficient thinking and modelling process to develop verified and validated Petra programs.
 * The problem is how to go from the something which is poorly/unspecified within the domain, to a fully specified correct system in Petra.
 * As Petra is new language / paradigm for formal verification it is difficult to map directly from ideas to verified models, without strong process for doing so.
 */

public class Controller {

	private final RemoteControl remoteControl = new RemoteControl();
	private final AutoPilot autoPilot = new AutoPilot();

	private final Control control = new Control();

	public boolean rc(){return control.on() && !(autoPilot.land() && !autoPilot.flyHome()) && !(autoPilot.flyHome() && !autoPilot.land());}
	public boolean flyHome(){return control.on() && autoPilot.flyHome() && !autoPilot.land();}

	public boolean land(){return control.on() && autoPilot.land() && !autoPilot.flyHome();}

	public boolean grounded(){return control.off();}

	public void action(){
		if (grounded()){
			control.exit();
			assert(grounded());
		}
		if (flyHome()){
			control.logFlyHome();
			control.turnOff();
			assert(grounded());
		}
		if (land()){
			control.logLand();
			autoPilot.update();
			assert(flyHome() ^ land() ^ rc());
		}
		if (rc()){
			control.logRC();
			remoteControl.processCommand();
			autoPilot.update();
			assert(flyHome() ^ land() ^ rc());
		}
	}
}