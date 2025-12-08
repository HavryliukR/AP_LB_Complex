package tourapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelSettersTest {

    @Test
    void settersAndGettersWorkForLocationHotelAndTour() {
        Location location = new Location("A", "B");
        location.setCountry("C");
        location.setCity("D");
        assertEquals("C", location.getCountry());
        assertEquals("D", location.getCity());

        Hotel hotel = new Hotel("H1", 3, 7.0);
        hotel.setName("H2");
        hotel.setStars(5);
        hotel.setRating(9.0);
        assertEquals("H2", hotel.getName());
        assertEquals(5, hotel.getStars());
        assertEquals(9.0, hotel.getRating());

        Tour tour = new Tour("T1", TourType.RELAX, location, hotel,
                TransportType.PLANE, FoodType.BB, 5, 500.0, 8.0, "D1");
        tour.setId(42L);
        tour.setName("T2");
        tour.setType(TourType.EXCURSION);
        Location loc2 = new Location("X", "Y");
        tour.setLocation(loc2);
        Hotel hotel2 = new Hotel("H3", 4, 8.5);
        tour.setHotel(hotel2);
        tour.setTransportType(TransportType.BUS);
        tour.setFoodType(FoodType.AI);
        tour.setDays(10);
        tour.setPrice(999.0);
        tour.setRating(9.9);
        tour.setDescription("D2");

        assertEquals(42L, tour.getId());
        assertEquals("T2", tour.getName());
        assertEquals(TourType.EXCURSION, tour.getType());
        assertSame(loc2, tour.getLocation());
        assertSame(hotel2, tour.getHotel());
        assertEquals(TransportType.BUS, tour.getTransportType());
        assertEquals(FoodType.AI, tour.getFoodType());
        assertEquals(10, tour.getDays());
        assertEquals(999.0, tour.getPrice());
        assertEquals(9.9, tour.getRating());
        assertEquals("D2", tour.getDescription());
    }

    @Test
    void customerGettersReturnValues() {
        Customer customer = new Customer(7L, "Name", "123", "email@example.com");
        assertEquals(7L, customer.getId());
        assertEquals("Name", customer.getFullName());
        assertEquals("123", customer.getPhone());
        assertEquals("email@example.com", customer.getEmail());
    }
}
