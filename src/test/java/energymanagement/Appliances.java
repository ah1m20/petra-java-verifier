package energymanagement;


import ast.terms.Base;

@Base public class Appliances {

    private int i = 0;
    private final double[] appliances = new double[100];
    private final double applianceMaxPower = 656;
    private double totalEnergy;
    private final double highPercentageFactor = 0.5;

    public boolean noReading(){ return i==0; }
    public boolean highUsage(){ return i==appliances.length && totalEnergy > appliances.length* applianceMaxPower *highPercentageFactor; }
    public boolean lowUsage(){ return i==appliances.length && !highUsage();}

    public void sumEnergy(){
        if (noReading()){ // P(x) => I(x), establishes invariant
            while(!(lowUsage() ^ highUsage())){ // P(x) => LC(x), loop starts
                // step(x)
                totalEnergy += Math.random()* applianceMaxPower;
                assert i>=0 && i< appliances.length; // step(x)=x' => I(x'), loop maintains invariant
            }
            assert(lowUsage() ^ highUsage()); // Q(x) => I(x), loop terminates and maintains invariant
        }
    }


    public void logTotalEnergy(){
        if (lowUsage() ^ highUsage()){
            CsvLogger.log(this.getClass().getSimpleName(),Double.toString(totalEnergy));
            assert(lowUsage() ^ highUsage());
        }
    }



}
