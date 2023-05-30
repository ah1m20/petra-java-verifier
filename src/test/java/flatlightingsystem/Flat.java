package flatlightingsystem;

import static ast.interp.util.Program.par;

public class Flat {
	private final Room front = new Room();
	private final Room kitchen = new Room();
	private final Room bedroom = new Room();
	public boolean all() { return front.bothOn() && kitchen.bothOn() && bedroom.bothOn(); }
	public boolean downstairs() { return front.bothOn() && kitchen.bothOn() && !bedroom.bothOn(); }
	public boolean upstairs() { return !front.bothOn() && !kitchen.bothOn() && bedroom.bothOn(); }
	public boolean none() { return !front.bothOn() && !kitchen.bothOn() && !bedroom.bothOn(); }

	public boolean other() { return !(front.bothOn() && kitchen.bothOn() && bedroom.bothOn()) &&
								!(front.bothOn() && kitchen.bothOn() && !bedroom.bothOn()) &&
								!(!front.bothOn() && !kitchen.bothOn() && bedroom.bothOn()) &&
								!(!front.bothOn() && !kitchen.bothOn() && !bedroom.bothOn()); }

	public void toggle() {
		if (other()){
			par(()->bedroom.turnOff(),
				()-> front.turnOff(),
				()-> kitchen.turnOff());
			assert(none());
		}
		if (all()){
			bedroom.toggle();
			assert(downstairs());
		}
		if (downstairs()){
			par(()-> front.toggle(),
			    ()-> kitchen.toggle());
			bedroom.toggle();
			assert(upstairs());
		}
		if (upstairs()){
			bedroom.toggle();
			assert(none());
		}
		if (none()){
			bedroom.toggle();
			par(()-> front.toggle(),
				()-> kitchen.toggle());
			assert(all());
		}
	}
}