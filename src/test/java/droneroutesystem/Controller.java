package droneroutesystem;

/*
 * Work towards a new model for drone, which was developed by thinking in terms of Hierachical State Machines (HSM). This seem to be a close fit to Petra whilst being wildy used and being a simple visual tool to prototype the model.
 * I believe the model is close to being verified and thinking in terms of HSMs helped this. Work should be done on seeing how to have an efficient thinking and modelling process to develop verified and validated Petra programs.
 * The problem is how to go from the something which is poorly/unspecified within the domain, to a fully specified correct system in Petra.
 * As Petra is new language / paradigm for formal verification it is difficult to map directly from ideas to verified models, without strong process for doing so.
 */

import ast.terms.Initial;

import static ast.interp.util.Program.par;

public class Controller implements Runnable {
	private final SysWrapper sys = new SysWrapper();
	private final RoutePlan routePlan = new RoutePlan();
	private final Diagnostics diagnostics = new Diagnostics();
	private final Control control = new Control();

	@Initial public boolean routeActive(){return control.on() && diagnostics.ok();}

	public boolean flyHome(){return control.on() && diagnostics.flyHomeImmediately();}

	public boolean grounded(){return control.off();}

	public boolean temperatureWarning(){
		return control.on() && diagnostics.temperatureWarning();}

	public void run(){
		if (grounded()){
			sys.exit();
			assert(grounded());
		} else if (flyHome()){
			par(sys::logLand,
				routePlan::returnToHome,
				control::turnOff);
			assert(grounded());
		} else if (routeActive()){
			par(sys::logRouteActive,routePlan::travel);
			assert(routeActive());
		} else if (temperatureWarning()){
			sys.logTemperatureWarning();
			assert(temperatureWarning());
		}
	}
}