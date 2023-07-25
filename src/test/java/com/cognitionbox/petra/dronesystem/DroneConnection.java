package com.cognitionbox.petra.dronesystem;

public class DroneConnection implements Runnable {

    private DroneConnection(){}

    private static DroneConnection droneConnection = new DroneConnection();

    public static DroneConnection getDroneConnection() {
        return droneConnection;
    }

    private volatile double rc;
    private volatile int temp = 0;
    private volatile double battery = 100;
    private volatile double snr = 1;

    public boolean low() { return temp < 30; }
    public boolean normal() { return temp >= 30 && temp <= 70; }
    public boolean high() { return temp > 70; }

    public boolean okLevel() {
        return battery >= 50;
    }

    public boolean returnHomeLevel(){return battery < 50;}

    private void readRc() {
        this.rc = Math.random();
    }

    private void readTemp() {
        this.temp = (int)(Math.random()*100);
    }

    private void readBattery() {
        this.battery =  (int)(Math.random()*100);
    }

    private void readSNR() {
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

    private volatile int x;
    private volatile int y;
    private volatile int z;

    private void readXYZ() {
        this.x = (int) (Math.random()*1000);
        this.y = (int) (Math.random()*1000);
        this.z = (int) (Math.random()*1000);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
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
        return (int)(Math.random()*100);
    }

    public void goToXYZ(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void goForward() {}
    public void goBackward() {}
    public void goRight() {}
    public void goLeft() {}

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

    @Override
    public void run() {
        readXYZ();
        readRc();
        readTemp();
        readSNR();
        readBattery();
    }
}
