package energymanagement;


public class EnergyManagementMain {
    public static void main(String... args) throws InterruptedException {
        while(true){
            new EnergyManagement().run();
            Thread.sleep(10);
        }
    }
}
