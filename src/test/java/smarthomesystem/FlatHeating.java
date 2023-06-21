package smarthomesystem;

import static ast.interp.util.Program.par;

public class FlatHeating {
	private final RoomHeating front = new RoomHeating();
	private final RoomHeating kitchen = new RoomHeating();
	private final RoomHeating bedroom = new RoomHeating();
	public boolean all() { return front.on() && kitchen.on() && bedroom.on(); }
	public boolean downstairs() { return front.on() && kitchen.on() && !bedroom.on(); }
	public boolean upstairs() { return !front.on() && !kitchen.on() && bedroom.on(); }
	public boolean none() { return !front.on() && !kitchen.on() && !bedroom.on(); }
	public void toggle() {
		if (all()){
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
			par(()-> bedroom.toggle(),
				()-> front.toggle(),
				()-> kitchen.toggle());
			assert(all());
		}
	}
}