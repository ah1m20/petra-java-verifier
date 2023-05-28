package dronesystem;

public class Main {
    public static void main(String[] args){
        Controller controller = new Controller();
        while(true){
            controller.action();
        }
    }
}
