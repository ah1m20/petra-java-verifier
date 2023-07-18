package com.cognitionbox.petra.dronesystem;

public class Controls {
    private final Position position = new Position();
    private final Power power = new Power();


    public boolean onLand() {
        return position.onLand();
    }

    public boolean inAir() {
        return position.inAir();
    }

    public boolean atHomeGrounded() {
        return power.off() && position.atHome();
    }
    public boolean atHomeNotGrounded() {
        return power.on() && position.atHome();
    }



    public void travelToHomeAndWaitTillHome() {
        if (onLand() ^ inAir()){
            position.travelToHomeAndWaitTillHome();
            power.exit();
            assert(atHomeGrounded());
        }
    }

    public void landAndWaitTillLanded() {
        if (inAir()){
            position.landAndWaitTillLanded();
            assert(onLand());
        }
    }

    public void takeOffAndWaitTillInAir() {
        if (onLand()){
            position.takeOffAndWaitTillInAir();
            assert(inAir());
        }
    }
}
