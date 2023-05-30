package dronesystem;

public class AutoPilot {
    private final Land land = new Land();
    private final FlyHome flyHome = new FlyHome();
    public boolean none(){return !(land.land() && flyHome.notFlyHome()) && !(land.notLand() && flyHome.flyHome());}

    public boolean land(){return land.land() && flyHome.notFlyHome();}

    public boolean flyHome(){return land.notLand() && flyHome.flyHome();}

    public void update() {
        if (none() ^ land() ^ flyHome()){
            land.read();
            flyHome.read();
            assert(none() ^ land() ^ flyHome());
        }
    }
}
