package droneroutesystem;

public class Sys {
    public void exit(){
        System.exit(0);
    }
    public void logTurnOn(){
        System.out.println("turnOn");
    }
    public void logTurnOff(){
        System.out.println("turnOff");
    }

    public void logFlyHome(){
        System.out.println("logFlyHome");
    }

    public void logLand(){
        System.out.println("logLand");
    }

    public void logRouteActive(){
        System.out.println("logRouteActive");
    }

    public void logWaitUntilHome() {
        System.out.println("waitUntilHome");
    }

    public void sleep() {
        try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
    }

    public void logWaitUntilLanded() {
        System.out.println("waitUntilLanded");
    }

    public void logWaitUntilTakenOff() {
        System.out.println("waitUntilTakenOff");
    }

    public void logTravelToHome() {
        System.out.println("travelToHome");
    }

    public void logWaitUntilC() {
        System.out.println("waitUntilC");
    }

    public void logWaitUntilB() {
        System.out.println("waitUntilB");
    }

    public void logWaitUntilA() {
        System.out.println("waitUntilA");
    }

    public void logTravelFromGroundToTakeOff() {
        System.out.println("travelFromGroundToTakeOff");
    }

    public void logTravelFromGroundToA() {
        System.out.println("travelFromGroundToA");
    }

    public void logTravelFromAToB() {
        System.out.println("travelFromAToB");
    }

    public void logTravelFromBToC() {
        System.out.println("travelFromBToC");
    }

    public void logTravelToLand() {
        System.out.println("travelToLand");
    }

    public void logTemperatureWarning() {
        System.out.println("temperatureWarning");
    }
}
