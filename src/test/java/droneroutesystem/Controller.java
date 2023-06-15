package droneroutesystem;

/*
 * Work towards a new model for drone, which was developed by thinking in terms of Hierachical State Machines (HSM). This seem to be a close fit to Petra whilst being wildy used and being a simple visual tool to prototype the model.
 * I believe the model is close to being verified and thinking in terms of HSMs helped this. Work should be done on seeing how to have an efficient thinking and modelling process to develop verified and validated Petra programs.
 * The problem is how to go from the something which is poorly/unspecified within the domain, to a fully specified correct system in Petra.
 * As Petra is new language / paradigm for formal verification it is difficult to map directly from ideas to verified models, without strong process for doing so.
 */

public class Controller implements Runnable {

	private final RoutePlan routePlan = new RoutePlan();
	private final AutoPilot autoPilot = new AutoPilot();

	private final Control control = new Control();

	public boolean routeActive(){return control.on() && autoPilot.none();}
	public boolean flyHome(){return control.on() && autoPilot.flyHome();}

	public boolean land(){return control.on() && autoPilot.land();}

	public boolean grounded(){return control.off();}

	public void run(){
		if (grounded()){
			control.exit();
			assert(grounded());
		}
		if (flyHome()){
			control.turnOff();
			routePlan.returnToHome();
			assert(grounded());
		}
		if (land()){
			control.logLand();
			routePlan.land();
			assert(flyHome() ^ land() ^ routeActive());
		}
		if (routeActive()){
			control.logRC();
			routePlan.travel();
			assert(flyHome() ^ land() ^ routeActive());
		}
	}
}