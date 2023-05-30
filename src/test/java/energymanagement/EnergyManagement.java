package energymanagement;

public class EnergyManagement {

    private final Logger logger = new Logger();
    private final Library library = new Library();
    private final ZeplerBuilding zeplerBuilding = new ZeplerBuilding();

    public boolean noReading(){ return zeplerBuilding.noReading() && library.noReading(); }

    public boolean high(){ return (zeplerBuilding.highUsage() || library.highUsage()) && !(zeplerBuilding.noReading() && library.noReading()); }

    public boolean low(){ return !(zeplerBuilding.highUsage() || library.highUsage()) && !(zeplerBuilding.noReading() && library.noReading()); }


    public void run() {
        if (noReading()){
            library.readData();
            zeplerBuilding.readData();
            assert(high() ^ low());
        }
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
