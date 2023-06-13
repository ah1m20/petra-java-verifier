package energymanagement;


import ast.terms.Base;

@Base
public class Heaters {

    private int i = 0;
    private final double[] heaters = new double[100];
    private final double heaterMaxPower = 65;
    private double totalEnergy;
    private final double highPercentageFactor = 0.5;

    public boolean noReading(){ return i==0; }
    public boolean highUsage(){ return i== heaters.length && totalEnergy > heaters.length* heaterMaxPower *highPercentageFactor; }
    public boolean lowUsage(){ return i== heaters.length && !highUsage();}

    public void readFromMeters(){
        if (noReading()){ // P(x) => I(x), establishes invariant
            while(!(lowUsage() ^ highUsage())){ // P(x) => LC(x), loop starts
                // step(x)
                heaters[i++] = Math.random()* heaterMaxPower;
                assert i>=0 && i< heaters.length; // step(x)=x' => I(x'), loop maintains invariant
            }
            assert(lowUsage() ^ highUsage()); // Q(x) => I(x), loop terminates and maintains invariant
        }
    }


    public void resetCounter(){
        if (lowUsage() ^ highUsage()){
            i = 0;
            assert(noReading());
        }
    }


    public void sumEnergy(){
        if (noReading()){ // P(x) => I(x), establishes invariant
            while(!(lowUsage() ^ highUsage())){ // P(x) => LC(x), loop starts
                // step(x)
                totalEnergy += heaters[i++];
                assert i>=0 && i< heaters.length; // step(x)=x' => I(x'), loop maintains invariant
            }
            assert(lowUsage() ^ highUsage()); // Q(x) => I(x), loop terminates and maintains invariant
        }
    }


    public void logTotalEnergy(){
        if (lowUsage() ^ highUsage()){
            CsvLogger.log(getClass().getSimpleName(),Double.toString(totalEnergy));
            assert(lowUsage() ^ highUsage());
        }
    }



}
