package dronesystem;

public class AutoPilot {
    private final Land land = new Land();
    private final FlyHome flyHome = new FlyHome();
    public boolean none(){return land.notLand() && flyHome.notFlyHome();}

    /* Adding && flyHome.notFlyHome() to the predicate below causes a gap in the coverage and
     * hence if not caught can cause both reachability/liveness issues, hence why the additional complete coverage
     * of predicates wrt to the underlying state space has been added as a requirement to all objects.
     * Currently the check is also place at the root object, this can be removed as checking all objects covers this and
     * so all thats needed at the root object is to check the public symbolic state space is covered by the entry point.
     */
    public boolean land(){return land.land();}

    public boolean flyHome(){return land.notLand() && flyHome.flyHome();}

    public void update() {
        if (none() ^ land() ^ flyHome()){
            land.read();
            flyHome.read();
            assert(none() ^ land() ^ flyHome());
        }
    }
}
