package droneroutesystem;

public final class Land {
    private final Temperature temperature  = new Temperature();

    public boolean land(){
        return temperature.high();
    }

    public boolean notLand(){
        return !temperature.high();
    }

    public void read(){
        if (land() ^ notLand()){
            temperature.read();
            assert(land() ^ notLand());
        }
    }
}
