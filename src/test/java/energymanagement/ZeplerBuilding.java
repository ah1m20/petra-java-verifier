package energymanagement;

public class ZeplerBuilding {

    private final ComputerRoom computerRoom = new ComputerRoom();

    public boolean noReading(){ return computerRoom.noReading(); }

    public boolean highUsage(){ return computerRoom.highUsage(); }
    public boolean lowUsage(){ return computerRoom.lowUsage(); }


     public void readData(){
        if (noReading() ^ highUsage() ^ lowUsage()){
            computerRoom.readData();
            assert(highUsage() ^ lowUsage());
        }
    }
}
