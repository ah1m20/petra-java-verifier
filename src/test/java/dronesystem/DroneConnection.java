package dronesystem;

public class DroneConnection {

    private DroneConnection(){}

    private static DroneConnection droneConnection = new DroneConnection();
    static {
        droneConnection.readRc();
        droneConnection.readSNR();
        droneConnection.readTemp();
    }

    public static DroneConnection getDroneConnection() {
        return droneConnection;
    }

    private volatile double rc;
    private volatile int temp;
    private volatile double battery;
    private volatile double snr;

    public boolean low() { return temp < 30; }
    public boolean normal() { return temp >= 30 && temp <= 70; }
    public boolean high() { return temp > 70; }

    public boolean okLevel() {
        return battery >= 50;
    }

    public boolean returnHomeLevel(){return battery < 50;}

    public void readRc() {
        this.rc = Math.random();
    }

    public void readTemp() {
        this.temp = (int)(Math.random()*100);
    }

    public void readBattery() {
        this.battery =  (int)(Math.random()*100);
    }

    public void readSNR() {
        this.snr = Math.random();
    }

    public boolean right() {
        return this.rc >=0 && this.rc <0.25;
    }

    public boolean left() {
        return this.rc >=0.25 && this.rc <0.5;
    }

    public boolean forward() {
        return this.rc >=0.5 && this.rc <0.75;
    }

    public boolean back() {
        return this.rc >=0.75 && this.rc <1;
    }

    public int getX() {
        return 0;
    }

    public int getY() {
        return 0;
    }

    public int getZ() {
        return 0;
    }

    public int getVelocityX() {
        return 0;
    }

    public int getVelocityY() {
        return 0;
    }
    public int getVelocityZ() {
        return 0;
    }


    public int getPower() {
        return 0;
    }

    public int getTemp() {
        return 0;
    }

    public void goToXYZ(int x, int y, int z) {
    }

    public void land() {
    }

    public float getSNR() {
        return 0;
    }


    public boolean lowSNR() {
        return  this.snr < 0.2;
    }

    public boolean normalSNR() {
        return this.snr >= 0.2 && this.snr <= 0.6;
    }

    public boolean highSNR() {
        return this.snr > 0.6;
    }
}
