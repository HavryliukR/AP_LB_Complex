package tourapp.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tourapp.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TourCatalogManagerTest {

    private TourCatalogManager manager;

    @BeforeEach
    void setUp() {
        manager = new TourCatalogManager();

        manager.addTour(createTour("Relax Greece", TourType.RELAX, "Greece", "Athens", 7, 900.0, 8.9));
        manager.addTour(createTour("Rome Weekend", TourType.EXCURSION, "Italy", "Rome", 3, 450.0, 8.2));
        manager.addTour(createTour("Spa Karlovy Vary", TourType.TREATMENT, "CzechRepublic", "KarlovyVary", 10, 1200.0, 9.0));
    }

    private Tour createTour(String name, TourType type, String country, String city, int days, double price, double rating) {
        Location location = new Location(country, city);
        Hotel hotel = new Hotel("Test Hotel", 4, 8.5);
        return new Tour(name, type, location, hotel, TransportType.PLANE, FoodType.BB, days, price, rating, "Test description");
    }

    @Test
    void addTourAssignsIdAndAddsToList() {
        int initialSize = manager.getAllTours().size();
        Tour tour = createTour("New Tour", TourType.RELAX, "Spain", "Barcelona", 5, 700.0, 8.0);

        Tour added = manager.addTour(tour);

        assertNotNull(added);
        assertTrue(added.getId() > 0);
        assertEquals(initialSize + 1, manager.getAllTours().size());
    }

    @Test
    void updateTourUpdatesExistingTour() {
        Tour existing = manager.getAllTours().get(0);
        long id = existing.getId();

        Location newLocation = new Location("Italy", "Venice");
        Hotel newHotel = new Hotel("New Hotel", 5, 9.5);
        Tour updated = new Tour("Updated Name", TourType.CRUISE, newLocation, newHotel,
                TransportType.SHIP, FoodType.AI, 8, 1500.0, 9.1, "Updated description");

        boolean ok = manager.updateTour(id, updated);

        assertTrue(ok);
        Tour after = manager.findTourById(id);
        assertEquals("Updated Name", after.getName());
        assertEquals(TourType.CRUISE, after.getType());
        assertEquals("Italy", after.getLocation().getCountry());
        assertEquals("New Hotel", after.getHotel().getName());
        assertEquals(8, after.getDays());
        assertEquals(1500.0, after.getPrice());
    }

    @Test
    void updateTourReturnsFalseForUnknownId() {
        Tour updated = createTour("X", TourType.RELAX, "X", "X", 1, 1.0, 1.0);
        boolean ok = manager.updateTour(9999L, updated);
        assertFalse(ok);
    }

    @Test
    void deleteTourRemovesTourAndRelatedData() {
        Tour tour = manager.getAllTours().get(0);
        long id = tour.getId();
        manager.addToFavorites(id);
        Customer customer = new Customer(1L, "Test User", "000", "test@example.com");
        manager.createBooking(id, customer, 2);

        boolean removed = manager.deleteTour(id);

        assertTrue(removed);
        assertNull(manager.findTourById(id));
        assertTrue(manager.getFavoriteTours().stream().noneMatch(t -> t.getId() == id));
        assertTrue(manager.getBookings().stream().noneMatch(b -> b.getTour().getId() == id));
    }

    @Test
    void deleteTourReturnsFalseForUnknownId() {
        boolean removed = manager.deleteTour(9999L);
        assertFalse(removed);
    }

    @Test
    void findToursFiltersByCriteria() {
        List<Tour> result = manager.findTours(
                TourType.RELAX,
                "Greece",
                TransportType.PLANE,
                null,
                5,
                10,
                800.0,
                1000.0
        );

        assertEquals(1, result.size());
        Tour tour = result.get(0);
        assertEquals("Relax Greece", tour.getName());
    }

    @Test
    void findToursReturnsEmptyWhenNoMatch() {
        List<Tour> result = manager.findTours(
                TourType.RELAX,
                "Mars",
                null,
                null,
                null,
                null,
                null,
                null
        );
        assertTrue(result.isEmpty());
    }

    @Test
    void getToursSortedByPriceAscending() {
        List<Tour> sorted = manager.getToursSortedBy(SortField.PRICE, true);

        assertEquals(3, sorted.size());
        assertTrue(sorted.get(0).getPrice() <= sorted.get(1).getPrice());
        assertTrue(sorted.get(1).getPrice() <= sorted.get(2).getPrice());
    }

    @Test
    void getToursSortedByDaysDescending() {
        List<Tour> sorted = manager.getToursSortedBy(SortField.DAYS, false);

        assertEquals(3, sorted.size());
        assertTrue(sorted.get(0).getDays() >= sorted.get(1).getDays());
        assertTrue(sorted.get(1).getDays() >= sorted.get(2).getDays());
    }

    @Test
    void getToursSortedByRatingAscending() {
        List<Tour> sorted = manager.getToursSortedBy(SortField.RATING, true);

        assertEquals(3, sorted.size());
        assertTrue(sorted.get(0).getRating() <= sorted.get(1).getRating());
    }

    @Test
    void sortToursCallsPrint() {
        // Just ensure no exception for both directions
        manager.sortTours(SortField.PRICE, true);
        manager.sortTours(SortField.PRICE, false);
    }

    @Test
    void addToFavoritesAndRemoveFromFavorites() {
        Tour tour = manager.getAllTours().get(0);
        long id = tour.getId();

        assertTrue(manager.addToFavorites(id));
        assertFalse(manager.addToFavorites(id)); // cannot add twice

        assertEquals(1, manager.getFavoriteTours().size());
        assertTrue(manager.removeFromFavorites(id));
        assertEquals(0, manager.getFavoriteTours().size());
    }

    @Test
    void addToFavoritesFailsForUnknownId() {
        assertFalse(manager.addToFavorites(9999L));
    }

    @Test
    void createAndCancelBooking() {
        Tour tour = manager.getAllTours().get(0);
        long id = tour.getId();

        Customer customer = new Customer(1L, "User", "123", "user@example.com");
        Booking booking = manager.createBooking(id, customer, 3);

        assertNotNull(booking);
        assertEquals(BookingStatus.NEW, booking.getStatus());
        assertEquals(1, manager.getBookings().size());

        boolean canceled = manager.cancelBooking(booking.getId());
        assertTrue(canceled);
        assertEquals(BookingStatus.CANCELED, manager.getBookings().get(0).getStatus());
    }

    @Test
    void createBookingReturnsNullForUnknownTour() {
        Customer customer = new Customer(1L, "User", "123", "user@example.com");
        Booking booking = manager.createBooking(9999L, customer, 2);
        assertNull(booking);
    }

    @Test
    void manageFavoritesDoesNotThrowOnEmptyAndNonEmpty() {
        manager.manageFavorites(); // empty favorites
        manager.addToFavorites(manager.getAllTours().get(0).getId());
        manager.manageFavorites(); // non empty
    }

    @Test
    void manageBookingsDoesNotThrowOnEmptyAndNonEmpty() {
        manager.manageBookings(); // no bookings yet
        Customer customer = new Customer(1L, "User", "123", "user@example.com");
        manager.createBooking(manager.getAllTours().get(0).getId(), customer, 2);
        manager.manageBookings(); // with bookings
    }

    @Test
    void viewAllToursHandlesEmptyList() {
        TourCatalogManager emptyManager = new TourCatalogManager();
        emptyManager.viewAllTours(); // should print "No tours found."
    }

    @Test
    void searchAndFilterToursWrapperExecutes() {
        manager.searchAndFilterTours(
                TourType.RELAX,
                "Greece",
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Test
    void loadAndSaveToFile(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("tours.txt");
        manager.saveToFile(file.toString());
        assertTrue(Files.exists(file));

        TourCatalogManager other = new TourCatalogManager();
        other.loadFromFile(file.toString());

        List<Tour> loaded = other.getAllTours();
        assertEquals(manager.getAllTours().size(), loaded.size());
        assertEquals(manager.getAllTours().get(0).getName(), loaded.get(0).getName());
    }

    @Test
    void loadFromMissingFileDoesNotThrow() {
        manager.loadFromFile("non_existing_file_12345.txt");
        assertEquals(3, manager.getAllTours().size());
    }

    @Test
    void loadFromFileSkipsInvalidLines(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("bad_tours.txt");
        String content = ""
                + "# comment line\n"
                + "\n"
                + "invalid|line|with|few|fields\n"
                + "1|Name|RELAX|Country|City|Hotel|4|8.0|PLANE|BB|7|700.0|8.0|Desc\n"
                + "2|BadNumber|RELAX|Country|City|Hotel|X|notNumber|PLANE|BB|7|700.0|8.0|Desc\n";
        Files.writeString(file, content);

        manager.loadFromFile(file.toString());

        assertEquals(1, manager.getAllTours().size());
        assertEquals("Name", manager.getAllTours().get(0).getName());
    }

    @Test
    void saveToFileDoesNotThrowOnEmptyCatalog(@TempDir Path tempDir) {
        TourCatalogManager empty = new TourCatalogManager();
        Path file = tempDir.resolve("empty.txt");
        empty.saveToFile(file.toString());
        assertTrue(Files.exists(file));
    }

    @Test
    void viewAllToursHandlesNonEmptyList() {
        // manager з setUp уже містить 3 тура
        manager.viewAllTours(); // просто перевіряємо, що гілка з for-loop відпрацьовує без помилки
        assertEquals(3, manager.getAllTours().size());
    }

    @Test
    void removeFromFavoritesReturnsFalseWhenTourNotInFavorites() {
        // favorites порожній
        boolean result = manager.removeFromFavorites(9999L);
        assertFalse(result);
    }

    @Test
    void saveToFileHandlesNullDescription(@TempDir Path tempDir) {
        TourCatalogManager m = new TourCatalogManager();

        Location location = new Location("Country", "City");
        Hotel hotel = new Hotel("Hotel", 4, 8.0);
        Tour tour = new Tour("Name", TourType.RELAX, location, hotel,
                TransportType.BUS, FoodType.BB, 5, 500.0, 8.0, null); // description = null
        m.addTour(tour);

        Path file = tempDir.resolve("null_desc.txt");
        m.saveToFile(file.toString());

        assertTrue(Files.exists(file));
    }
    @Test
    void sortToursHandlesEmptyCatalog() {
        TourCatalogManager empty = new TourCatalogManager();
        // просто має відпрацювати гілка sorted.isEmpty()
        empty.sortTours(SortField.PRICE, true);
    }

    @Test
    void searchAndFilterToursWithNoResults() {
        // поточний manager з setUp має 3 тура, але країна інша
        manager.searchAndFilterTours(
                TourType.RELAX,
                "NonExistingCountry",
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Test
    void removeFromFavoritesReturnsFalseForNonExistingId() {
        boolean result = manager.removeFromFavorites(9999L);
        assertFalse(result);
    }


}
