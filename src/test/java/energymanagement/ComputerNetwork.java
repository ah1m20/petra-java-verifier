package energymanagement;


import ast.terms.Base;

@Base
public class ComputerNetwork {

    private int i = 0;
    private final double[] computers = new double[100];
    private final double computerMaxPower = 7.2;
    private double totalEnergy;
    private final double highPercentageFactor = 0.5;

    public boolean isNotZero(){return i!=0;}
    public boolean isZero(){return i==0;}
    public boolean isComputerLength(){return i==computers.length;}

    public boolean isLtComputerLength(){return i<computers.length;}
    public void readFromMeters(){
        if (isZero()){ // P(x) => I(x), establishes invariant
            while(isLtComputerLength()){ // P(x) => LC(x), loop starts
                // step(x)
                computers[i++] = Math.random()*computerMaxPower;
                assert i>=0 && i< computers.length; // step(x)=x' => I(x'), loop maintains invariant
            }
            assert(isComputerLength()); // Q(x) => I(x), loop terminates and maintains invariant
        }
    }


    public void resetCounter(){
        if (isNotZero()){
            i = 0;
            assert(isZero());
        }
    }

    public void sumEnergy(){
        if (isZero()){ // P(x) => I(x), establishes invariant
            while(isLtComputerLength()){ // P(x) => LC(x), loop starts
                // step(x)
                totalEnergy += computers[i++];
                assert i>=0 && i< computers.length; // step(x)=x' => I(x'), loop maintains invariant
            }
            assert(isComputerLength()); // Q(x) => I(x), loop terminates and maintains invariant
        }
    }


    public void logTotalEnergy(){
        if (lowUsage() ^ highUsage()){
            CsvLogger.log(this.getClass().getSimpleName(),Double.toString(totalEnergy));
            assert(lowUsage() ^ highUsage());
        }
    }

    public boolean noReading(){ return i==0; }
    public boolean highUsage(){ return i==computers.length && totalEnergy > computers.length*computerMaxPower*highPercentageFactor; }
    public boolean lowUsage(){ return i==computers.length && !highUsage();}

}
