package dronesystem;

/*
 * Work towards a new model for drone, which was developed by thinking in terms of Hierachical State Machines (HSM). This seem to be a close fit to Petra whilst being wildy used and being a simple visual tool to prototype the model.
 * I believe the model is close to being verified and thinking in terms of HSMs helped this. Work should be done on seeing how to have an efficient thinking and modelling process to develop verified and validated Petra programs.
 * The problem is how to go from the something which is poorly/unspecified within the domain, to a fully specified correct system in Petra.
 * As Petra is new language / paradigm for formal verification it is difficult to map directly from ideas to verified models, without strong process for doing so.
 */

import ast.terms.Initial;

public class Controller implements Runnable {

	private final Controls controls = new Controls();
	private final RemoteControl remoteControl = new RemoteControl();
	private final AutoPilot autoPilot = new AutoPilot();
	public boolean rc(){return autoPilot.none() && controls.inAir();}
	public boolean flyHome(){return autoPilot.flyHome() && controls.inAir();}

	public boolean land(){return autoPilot.land() && controls.inAir();}
	public boolean landed(){return controls.onLand();}
	public boolean atHomeAndGrounded(){return controls.atHomeGrounded();}
	@Initial
	public boolean atHomeAndNotGrounded(){return controls.atHomeNotGrounded();}

	public void run(){
		if (atHomeAndGrounded()){
			;
			assert(atHomeAndGrounded());
		}
		if (flyHome()){
			controls.travelToHomeAndWaitTillHome();
			assert(atHomeAndGrounded());
		}
		if (land()){
			controls.landAndWaitTillLanded();
			assert(landed());
		}
		if (atHomeAndNotGrounded() ^ landed()){
			controls.takeOffAndWaitTillInAir();
			assert(flyHome() ^ land() ^ rc());
		}
		if (rc()){
			remoteControl.processCommand();
			assert(flyHome() ^ land() ^ rc());
		}
	}
}