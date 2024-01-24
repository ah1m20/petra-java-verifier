package com.cognitionbox.petra.examples.buildingalarmsystem;

import com.cognitionbox.petra.ast.terms.Initial;

import static com.cognitionbox.petra.ast.interp.util.Program.par;

public class Building implements Runnable {
	private final Room front = new Room();
	private final Room kitchen = new Room();
	private final Room bedroom = new Room();
	public boolean all() { return front.bothArmed() && kitchen.bothArmed() && bedroom.bothArmed(); }
	public boolean downstairs() { return front.bothArmed() && kitchen.bothArmed() && !bedroom.bothArmed(); }
	public boolean upstairs() { return !front.bothArmed() && !kitchen.bothArmed() && bedroom.bothArmed(); }
	@Initial public boolean none() { return !front.bothArmed() && !kitchen.bothArmed() && !bedroom.bothArmed(); }

	public boolean other() { return !(front.bothArmed() && kitchen.bothArmed() && bedroom.bothArmed()) &&
								!(front.bothArmed() && kitchen.bothArmed() && !bedroom.bothArmed()) &&
								!(!front.bothArmed() && !kitchen.bothArmed() && bedroom.bothArmed()) &&
								!(!front.bothArmed() && !kitchen.bothArmed() && !bedroom.bothArmed()); }

	public void run() {
		if (other()){
			par(()->bedroom.disarm(),
				()-> front.disarm(),
				()-> kitchen.disarm());
			assert(none());
		} else if (all()){
			bedroom.toggle();
			assert(downstairs());
		} else if (downstairs()){
			par(()-> front.toggle(),
			    ()-> kitchen.toggle(),
				()-> bedroom.toggle());
			assert(upstairs());
		} else if (upstairs()){
			bedroom.toggle();
			assert(none());
		} else if (none()){
			par(()->bedroom.toggle(),
				()-> front.toggle(),
				()-> kitchen.toggle());
			assert(all());
		}
	}
}