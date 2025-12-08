package tourapp.ui.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tourapp.core.TourCatalogManager;
import tourapp.model.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class SaveToFileCommandTest {

    private Tour createSampleTour() {
        Location location = new Location("Country", "City");
        Hotel hotel = new Hotel("Hotel", 4, 8.0);
        return new Tour("Name", TourType.RELAX, location, hotel,
                TransportType.PLANE, FoodType.BB, 7, 700.0, 8.5, "Desc");
    }

    @Test
    void executeWithEmptyPathDoesNotFail() {
        String input = "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        manager.addTour(createSampleTour());
        ApplicationContext context = new ApplicationContext(scanner, manager);

        SaveToFileCommand command = new SaveToFileCommand(context);
        command.execute();

        // nothing to assert except no exception
        assertEquals(1, manager.getAllTours().size());
    }

    @Test
    void executeWithValidPathSavesData(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("out.txt");
        String input = file.toString() + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        manager.addTour(createSampleTour());
        ApplicationContext context = new ApplicationContext(scanner, manager);

        SaveToFileCommand command = new SaveToFileCommand(context);
        command.execute();

        assertTrue(Files.exists(file));
        String content = Files.readString(file);
        assertTrue(content.contains("Name"));
    }


    @Test
    void saveToFileHandlesIOException(@TempDir Path tempDir) {
        TourCatalogManager m = new TourCatalogManager();

        Location location = new Location("Country", "City");
        Hotel hotel = new Hotel("Hotel", 4, 8.0);
        m.addTour(new Tour("Name", TourType.RELAX, location, hotel,
                TransportType.BUS, FoodType.BB, 5, 500.0, 8.0, "Desc"));

        // Спроба писати в директорію, а не в файл -> IOException всередині методу
        m.saveToFile(tempDir.toString());

        // Головне, що не впало — метод ловить ексепшен сам
        assertEquals(1, m.getAllTours().size());
    }

}
