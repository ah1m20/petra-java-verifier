package smarthomesystem;

import static ast.interp.util.Program.par;

public class FlatLighting {
	private final RoomLighting front = new RoomLighting();
	private final RoomLighting kitchen = new RoomLighting();
	private final RoomLighting bedroom = new RoomLighting();
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
			    ()-> kitchen.toggle());
			bedroom.toggle();
			assert(upstairs());
		} else if (upstairs()){
			bedroom.toggle();
			assert(none());
		} else if (none()){
			bedroom.toggle();
			par(()-> front.toggle(),
				()-> kitchen.toggle());
			assert(all());
		}
	}
}