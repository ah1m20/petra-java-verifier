package energymanagement;

import static ast.interp.util.Program.par;

public class Library {
    private final Floor1 floor1 = new Floor1();

    public boolean noReading(){ return floor1.noReading(); }

    public boolean highUsage(){ return floor1.highUsage(); }
    public boolean lowUsage(){ return floor1.lowUsage(); }


    public void readData(){
        if (noReading()){
           floor1.readData1();
           floor1.readData2();
           floor1.readData3();
           assert(highUsage() ^ lowUsage());
        }
    }
}
