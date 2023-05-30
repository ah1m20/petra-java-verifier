package droneroutesystem;

public class FlyHome {

    private final Wifi wifi  = new Wifi();
    private final Battery battery  = new Battery();


    public boolean flyHome(){
        return wifi.lowSNR() || battery.returnHomeLevel();
    }

    public boolean notFlyHome(){
        return !(wifi.lowSNR() || battery.returnHomeLevel());
    }

    public void read(){
        if (flyHome() ^ notFlyHome()){
            wifi.read();
            battery.read();
            assert(flyHome() ^ notFlyHome());
        }
    }
}
