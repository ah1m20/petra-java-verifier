package hsm1;

public final class XYZ implements Runnable {
    private final static XYZ X_Y_Z = new XYZ();

    private XYZ() {
    }

    public static XYZ getInstance() {
        return X_Y_Z;
    }

    private double v = 0;

    public void run() {
        v = Math.random() * 100;
    }

    public boolean x() {return v < 33;}
    public boolean y() {return v >= 33 && v<= 66;}
    public boolean z() {return v > 66;}

}