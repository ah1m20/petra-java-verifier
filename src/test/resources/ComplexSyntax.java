public class ComplexSyntax {
	private final Light a = new Light();
	private final Light b = new Light();
	private final Light b1 = new Light();
	private final Light b2 = new Light();
	private final Light b3 = new Light();
	private final Light c = new Light();
	private final Light d = new Light();
	private final Light e = new Light();
	private final Light f = new Light();
	private final Light f1 = new Light();
	private final Light f2 = new Light();
	private final Light f3 = new Light();
	private final Light f4 = new Light();
	public boolean on() { return a.on() && b.on(); }
	public boolean off() { return c.off() && d.off(); }
	public void toggle() {
		if (off()){
			par(()-> b1.turnOff(), ()-> b2.turnOff(), ()-> b3.turnOff());
			assert(on());
		} else if (on()){
			par(()-> {
						a.turn();
						a.turnOff();
					},
				()-> b.turnOff(), ()-> b1.turnOff(), ()-> b2.turnOff(), ()-> b3.turnOff());
			assert(off());
		}
	}
}