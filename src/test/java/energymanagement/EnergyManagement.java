package energymanagement;

public class EnergyManagement implements Runnable {
    private final Logger logger = new Logger();
    private final Library library = new Library();
    private final ZeplerBuilding zeplerBuilding = new ZeplerBuilding();
    public boolean high(){ return (zeplerBuilding.highUsage() || library.highUsage()); }
    public boolean low(){ return !(zeplerBuilding.highUsage() || library.highUsage()); }

    public void run() {
        if (high()){
            logger.logUsageToReportHigh();
            assert(high() ^ low());
        }
        if (low()){
            logger.logUsageToReportLow();
            assert(high() ^ low());
        }
    }

}
