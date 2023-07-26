package com.cognitionbox.petra.examples.droneroutesystem;

import com.cognitionbox.petra.ast.terms.External;

@External
public class DroneConnection implements Runnable {

    private final Sys sys = new Sys();
    private final Waypoint home = new Waypoint(0,0,0);
    private final Waypoint takeOff = new Waypoint(0,0,100);
    private final Waypoint a = new Waypoint(10,0,100);
    private final Waypoint b = new Waypoint(10,10,100);
    private final Waypoint c = new Waypoint(20,20,100);

    private DroneConnection(){}

    private static DroneConnection droneConnection = new DroneConnection();

    public static DroneConnection getDroneConnection() {
        return droneConnection;
    }

    private volatile double rc;
    private volatile int temp = 0;
    private volatile double battery = 100;
    private volatile double snr = 1;

    private volatile int x;
    private volatile int y;
    private volatile int z;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public boolean atA(){
        return x==a.getX() &&  y==a.getY() &&  z==a.getZ();
    }

    public boolean atB(){
        return x==b.getX() &&  y==b.getY() &&  z==b.getZ();
    }

    public boolean atC(){
        return x==c.getX() &&  y==c.getY() &&  z==c.getZ();
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


    public void goTo(Waypoint waypoint){
        goToXYZ(waypoint.getX(), waypoint.getY(), waypoint.getZ());
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

    @Override
    public void run() {
        readSNR();
        readTemp();
        readBattery();
        readRc();
    }

    public void goToTakeoff() {
        goTo(takeOff);
    }

    public void goToHome(){
        sys.logTravelToHome();
        goTo(home);
    }

    public void goToA() {
        sys.logTravelFromGroundToA();
        goTo(a);
    }

    public void goToB() {
        sys.logTravelFromAToB();
        goTo(b);
    }

    public void goToC() {
        sys.logTravelFromBToC();
        goTo(c);
    }

    public void goToLand() {
        sys.logTravelToLand();
        Waypoint waypoint = new Waypoint(getX(),getY(),0);
        goToXYZ(waypoint.getX(), waypoint.getY(), waypoint.getZ());
    }

    //	public boolean onLand(){ return connection.getZ() == 0; }
//
//	public boolean atHome(){ return connection.getX() == 0 && connection.getY() == 0 && connection.getZ() == 10; }

    public boolean onLand(){ return getX() != 0 && getY() != 0 && getZ() == 0; }

    public boolean atHome(){ return getX() == 0 && getY() == 0 && getZ() == 0; }

    public void waitUntilHome() {
        sys.logWaitUntilHome();
        while(!atHome()){
            sys.sleep();
        }
        assert (atHome());
    }

    public void waitUntilLanded() {
        sys.logWaitUntilLanded();
        while(!onLand()){
            sys.sleep();
        }
        assert (onLand());
    }

    public void waitUntilA() {
        sys.logWaitUntilA();
        while(!atA()){
            sys.sleep();
        }
        assert (atA());
    }

    public void waitUntilB() {
        sys.logWaitUntilB();
        while(!atB()){
            sys.sleep();
        }
        assert (atB());
    }

    public void waitUntilC() {
        sys.logWaitUntilC();
        while(!atC()){
            sys.sleep();
        }
        assert (atC());
    }
}
