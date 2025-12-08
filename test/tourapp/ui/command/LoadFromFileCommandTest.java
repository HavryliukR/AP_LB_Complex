package tourapp.ui.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tourapp.core.TourCatalogManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class LoadFromFileCommandTest {

    @Test
    void executeWithEmptyPathDoesNotCallManager() {
        String input = "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        ApplicationContext context = new ApplicationContext(scanner, manager);

        LoadFromFileCommand command = new LoadFromFileCommand(context);
        command.execute();

        assertEquals(0, manager.getAllTours().size());
    }

    @Test
    void executeWithValidPathLoadsData(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("tours.txt");
        String content = "1|Name|RELAX|Country|City|Hotel|4|8.0|PLANE|BB|7|700.0|8.0|Desc\n";
        Files.writeString(file, content);

        String input = file.toString() + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        ApplicationContext context = new ApplicationContext(scanner, manager);

        LoadFromFileCommand command = new LoadFromFileCommand(context);
        command.execute();

        assertEquals(1, manager.getAllTours().size());
    }

    @Test
    void loadFromFileHandlesIOException(@TempDir Path tempDir) {
        // tempDir існує як директорія, newBufferedReader на ньому кине IOException
        TourCatalogManager m = new TourCatalogManager();
        m.loadFromFile(tempDir.toString());

        // просто перевіряємо, що жодного ексепшена не вилетіло і каталог залишився порожнім
        assertTrue(m.getAllTours().isEmpty());
    }

}
