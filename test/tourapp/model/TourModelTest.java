package tourapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TourModelTest {

    @Test
    void updateFromCopiesAllFields() {
        Location loc1 = new Location("Country1", "City1");
        Hotel hotel1 = new Hotel("Hotel1", 3, 7.5);
        Tour original = new Tour("Original", TourType.RELAX, loc1, hotel1,
                TransportType.PLANE, FoodType.BB, 5, 500.0, 8.0, "Desc1");
        original.setId(1L);

        Location loc2 = new Location("Country2", "City2");
        Hotel hotel2 = new Hotel("Hotel2", 4, 9.1);
        Tour source = new Tour("NewName", TourType.CRUISE, loc2, hotel2,
                TransportType.SHIP, FoodType.AI, 10, 1500.0, 9.5, "Desc2");

        original.updateFrom(source);

        assertEquals("NewName", original.getName());
        assertEquals(TourType.CRUISE, original.getType());
        assertEquals("Country2", original.getLocation().getCountry());
        assertEquals("Hotel2", original.getHotel().getName());
        assertEquals(TransportType.SHIP, original.getTransportType());
        assertEquals(FoodType.AI, original.getFoodType());
        assertEquals(10, original.getDays());
        assertEquals(1500.0, original.getPrice());
        assertEquals(9.5, original.getRating());
        assertEquals("Desc2", original.getDescription());
        assertEquals(1L, original.getId(), "Id should not change");
    }

    @Test
    void toStringProducesNonEmptyValue() {
        Location location = new Location("Country", "City");
        Hotel hotel = new Hotel("Hotel", 4, 8.5);
        Tour tour = new Tour("Name", TourType.EXCURSION, location, hotel,
                TransportType.TRAIN, FoodType.HB, 3, 300.0, 8.0, "Test");

        String str = tour.toString();
        assertNotNull(str);
        assertTrue(str.contains("Tour #"));
        assertTrue(str.contains("Name"));
    }

    @Test
    void locationToStringWorks() {
        Location location = new Location("X", "Y");
        assertEquals("X, Y", location.toString());
    }

    @Test
    void hotelToStringWorks() {
        Hotel hotel = new Hotel("H", 4, 9.0);
        String s = hotel.toString();
        assertTrue(s.contains("H"));
        assertTrue(s.contains("4*"));
    }

    @Test
    void bookingToStringWorks() {
        Location location = new Location("Country", "City");
        Hotel hotel = new Hotel("Hotel", 4, 8.5);
        Tour tour = new Tour("Name", TourType.RELAX, location, hotel,
                TransportType.BUS, FoodType.AI, 7, 700.0, 9.0, "Test");
        tour.setId(10L);

        Customer customer = new Customer(1L, "User", "123", "user@example.com");
        Booking booking = new Booking(5L, tour, customer, 2, BookingStatus.NEW);

        String s = booking.toString();
        assertTrue(s.contains("Booking #5"));
        assertTrue(s.contains("tour #10"));
        assertTrue(s.contains("User"));
    }

    @Test
    void toStringHandlesNullLocationAndHotel() {
        Tour tour = new Tour(
                "Name",
                TourType.RELAX,
                null,                // location = null
                null,                // hotel = null
                TransportType.BUS,
                FoodType.NO_MEAL,
                5,
                500.0,
                7.5,
                "Test"
        );

        String s = tour.toString();
        assertNotNull(s);
        assertTrue(s.contains("N/A"));
    }


}
