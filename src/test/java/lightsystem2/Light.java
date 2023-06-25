package lightsystem2;

import static ast.interp.util.Program.par;

public class Light {
	private final Power p1 = new Power();
	private final Control c1 = new Control();

	private final Power p2 = new Power();
	private final Control c2 = new Control();

	public boolean bothOn() { return p1.on() && c1.on() && p2.on() && c2.on(); }
	public boolean eitherOff() { return ( p1.off() || c1.off()) || ( p2.off() || c2.off()); }

	public void toggle() {
		if (eitherOff()){
			par(()-> {
				par(()-> p1.turnOn(),
						()-> c1.turnOn());
			}, ()-> {
				par(()-> p2.turnOn(),
						()-> c2.turnOn());
			});
			assert(bothOn());
		}
		if (bothOn()){
			par(()-> p1.turnOff(), ()-> c1.turnOff());
			par(()-> p2.turnOff(), ()-> c2.turnOff());
			assert(eitherOff());
		}
	}
}