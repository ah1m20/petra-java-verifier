package hsm2;

public final class Data implements Runnable {
    private final static Data data = new Data();

    private Data() {
    }

    public static Data getInstance() {
        return data;
    }

    private double u = 0;
    private double v = 0;
    private double w = 0;
    private double x = 0;
    private double y = 0;
    private double z = 0;

    public void run() {
        u = Math.random() * 100;
        v = Math.random() * 1000;
        w = Math.random() * 1000;
        x = Math.random() * 1000;
        y = Math.random() * 1000;
        z = Math.random() * 1000;
    }

    public boolean highV() {return v > 750;}
    public boolean lowV() {return v <= 750;}
    public boolean highW() {return w > 750;}
    public boolean lowW() {return w <= 750;}
    public boolean highX() {return x > 750;}
    public boolean lowX() {return x <= 750;}
    public boolean highY() {return y > 750;}
    public boolean lowY() {return y <= 750;}
    public boolean highZ() {return z > 750;}
    public boolean lowZ() {return z <= 750;}

    public boolean a() {return u < 33;}
    public boolean b() {return u >= 33 && u<= 66;}
    public boolean c() {return u > 66;}



}