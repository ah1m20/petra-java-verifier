package hsm2;

import static ast.interp.util.Program.startReactive;

public class Main {
    public static void main(String[] args) {
        startReactive(0,new M1(), Data.getInstance());
    }
}
