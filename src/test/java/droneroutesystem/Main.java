package droneroutesystem;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Controller controller = new Controller();
        while(true){
            controller.action();
            Thread.sleep(100);
        }
    }
}
