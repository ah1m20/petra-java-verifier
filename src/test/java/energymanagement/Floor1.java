package energymanagement;

public class Floor1 {

    private final AirConditioners airConditioners = new AirConditioners();
    private final Appliances appliances = new Appliances();
    private final Heaters heaters = new Heaters();
    private final Plumbing plumbing = new Plumbing();


    public boolean noReading(){
        return heaters.noReading() && airConditioners.noReading() && appliances.noReading();
    }
    public boolean lowUsage(){
        return heaters.lowUsage() && airConditioners.lowUsage() && appliances.lowUsage() && plumbing.lowUsage();
    }
    public boolean highUsage(){
        return heaters.highUsage() && airConditioners.highUsage() && appliances.highUsage() && plumbing.highUsage();
    }


    public void readData1(){
        if (noReading()){
            heaters.sumEnergy();
            heaters.logTotalEnergy();
            assert(lowUsage() ^ highUsage());
        }
    }

    public void readData2(){
        if (lowUsage() ^ highUsage()){
            airConditioners.sumEnergy();
            airConditioners.logTotalEnergy();
            assert(lowUsage() ^ highUsage());
        }
    }

    public void readData3(){
        if (lowUsage() ^ highUsage()){
            appliances.sumEnergy();
            appliances.logTotalEnergy();
            assert(lowUsage() ^ highUsage());
        }
    }

}
