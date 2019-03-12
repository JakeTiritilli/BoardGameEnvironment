package application.utility;

/**
 * MemoryVI
 */
public class MemoryVI extends ViewInitializer {
    final static String fxmlResource = "/views/memory/Memory.fxml";

    public static MemoryVI create() {
        return new MemoryVI();
    }

    public MemoryVI() {
        super(fxmlResource);
    }
}
