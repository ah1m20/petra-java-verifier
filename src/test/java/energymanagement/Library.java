package energymanagement;

public class Library {
    private final Floor1 floor1 = new Floor1();

    public boolean highUsage(){ return floor1.highUsage(); }
    public boolean lowUsage(){ return floor1.lowUsage(); }

}
