package simplethermostat;

public class Sensor implements Runnable {
    private final static Sensor sensor = new Sensor();

    private Sensor() {
    }

    public static Sensor getInstance() {
        return sensor;
    }

    // currently all infinite data e.g. real numbers is stored outside of Petra and is never set directly by Petra.
    // Petra only ever triggers updates or can set finite data points through abstractions.
    // Having a big database of config data is a good way to manage data and Petra can trigger reads and writes to it
    // using preset queries.
    private volatile int target;
    private volatile int temp;

    public boolean aboveOrEqualToTarget() { return temp > target; }
    public boolean belowTarget() { return temp <= target; }

    @Override
    public void run() {
        target = (int)(Math.random()*100);
        temp = (int)(Math.random()*100);
    }
}
