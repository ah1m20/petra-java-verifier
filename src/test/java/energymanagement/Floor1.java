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
        return !(heaters.lowUsage() && airConditioners.lowUsage() && appliances.lowUsage() && plumbing.lowUsage()) && !(heaters.noReading() && airConditioners.noReading() && appliances.noReading());
    }


    public void readData(){
        if (noReading()){
            heaters.readFromMeters();
            heaters.resetCounter();
            heaters.sumEnergy();
            heaters.logTotalEnergy();
            airConditioners.readFromMeters();
            airConditioners.resetCounter();
            airConditioners.sumEnergy();
            airConditioners.logTotalEnergy();
            appliances.readFromMeters();
            appliances.resetCounter();
            appliances.sumEnergy();
            appliances.logTotalEnergy();
            assert(lowUsage() ^ highUsage());
        }
    }
}
