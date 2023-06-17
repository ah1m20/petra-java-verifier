package energymanagement;


import ast.terms.Base;

@Base public class AirConditioners {

    private int i = 0;
    private final double[] airconditioners = new double[100];
    private final double airConditionerMaxPower = 200;
    private double totalEnergy;
    private final double highPercentageFactor = 0.5;

    public boolean isNotZero(){return i!=0;}
    public boolean isConditionerLength(){return i==airconditioners.length;}

    public boolean isLtConditionerLength(){return i<airconditioners.length;}


    public void sumEnergy(){
        if (noReading()){ // P(x) => I(x), establishes invariant
            while(isLtConditionerLength()){ // P(x) => LC(x), loop starts
                // step(x)
                totalEnergy += Math.random()* airConditionerMaxPower;
                assert i>=0 && i< airconditioners.length; // step(x)=x' => I(x'), loop maintains invariant
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

    public boolean noReading(){ return i==0; }
    public boolean highUsage(){ return i== airconditioners.length && totalEnergy > airconditioners.length* airConditionerMaxPower *highPercentageFactor; }
    public boolean lowUsage(){ return i== airconditioners.length && !highUsage();}

}
