public class Flat {
	private final Room front = new Room();
	private final Room kitchen = new Room();
	private final Room bedroom = new Room();
	public boolean all() { return front.on() && kitchen.on() && bedroom.on(); }
	public boolean downstairs() { return front.on() && kitchen.on() && !bedroom.on(); }
	public boolean upstairs() { return !front.on() && !kitchen.on() && bedroom.on(); }
	public boolean none() { return !front.on() && !kitchen.on() && !bedroom.on(); }
	public void toggle() {
		if (all()){
			bedroom.toggle();
			assert(downstairs());
		}
		if (downstairs()){
			front.toggle();
			kitchen.toggle();
			bedroom.toggle();
			assert(upstairs());
		}
		if (upstairs()){
			bedroom.toggle();
			assert(none());
		}
		if (none()){
			front.toggle();
			kitchen.toggle();
			bedroom.toggle();
			assert(all());
		}
	}
}