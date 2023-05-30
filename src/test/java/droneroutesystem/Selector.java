package droneroutesystem;

public class Selector {
    private int selection = 0;

    public boolean route(){
        return this.selection==0;
    }

    public boolean land(){
        return this.selection==1;
    }

    public boolean flyHome(){
        return this.selection==2;
    }
}
