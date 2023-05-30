package energymanagement;




public class ComputerRoom {

    private final ComputerNetwork computerNetwork = new ComputerNetwork();

    public boolean noReading(){
        return computerNetwork.noReading();
    }
    public boolean lowUsage(){
        return computerNetwork.lowUsage();
    }
    public boolean highUsage(){
        return computerNetwork.highUsage();
    }


    public void readData(){
        if (noReading()){
            computerNetwork.readFromMeters();
            computerNetwork.resetCounter();
            computerNetwork.sumEnergy();
            computerNetwork.logTotalEnergy();
            assert(lowUsage() ^ highUsage());
        }
    }
}
