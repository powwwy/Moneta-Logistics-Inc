import System.managers.ContainerManager;
import System.models.Container;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Container> container = ContainerManager.loadContainers();
        System.out.println("\nContainers:");
        for (Container k : container) {
            System.out.println(k);
        }
    }
}
