package simplethermostat;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thermostat thermostat = new Thermostat();
        while(true){
            thermostat.action();
            Thread.sleep(1000);
        }
    }
}
