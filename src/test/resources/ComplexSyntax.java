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
//		if (off()){
//			a.turnOn();
//			b.turnOn();
//			par(()-> b1.turnOff(), ()-> b2.turnOff(), ()-> b3.turnOff());
//			d.turnOn();
//			e.turnOn();
//			f.turnOn();
//			par(()-> f1.turnOff(), ()-> f2.turnOff(), ()-> f3.turnOff(), ()-> f4.turnOff());
//			assert(on());
//		}
		if (on()){
			par(()-> {
						a.turn();
						a.turnOff();
						par(()-> b1.turnOff(), ()-> b2.turnOff(), ()-> b3.turnOff());
					},
				()-> b.turnOff());
			assert(off());
		}
	}
}