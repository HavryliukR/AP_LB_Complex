package tourapp.ui.command;

import org.junit.jupiter.api.Test;
import tourapp.core.TourCatalogManager;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextTest {

    @Test
    void gettersReturnSameObjects() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();

        ApplicationContext context = new ApplicationContext(scanner, manager);

        assertSame(scanner, context.getScanner());
        assertSame(manager, context.getCatalogManager());
    }
}
