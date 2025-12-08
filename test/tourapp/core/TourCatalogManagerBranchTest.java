package tourapp.core;

import org.junit.jupiter.api.Test;
import tourapp.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TourCatalogManagerBranchTest {

    @Test
    void findToursSkipsToursWithNullLocationWhenCountryFilterUsed() {
        TourCatalogManager manager = new TourCatalogManager();

        // Tour with null location
        Hotel hotel = new Hotel("Hotel", 4, 8.0);
        Tour t1 = new Tour("NoLocation", TourType.RELAX, null, hotel,
                TransportType.BUS, FoodType.BB, 5, 500.0, 8.0, "No location");
        manager.addTour(t1);

        // Tour with proper location
        Location location = new Location("CountryX", "CityX");
        Tour t2 = new Tour("WithLocation", TourType.RELAX, location, hotel,
                TransportType.BUS, FoodType.BB, 5, 500.0, 8.0, "With location");
        manager.addTour(t2);

        List<Tour> result = manager.findTours(
                null,
                "CountryX",
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertEquals(1, result.size());
        assertEquals("WithLocation", result.get(0).getName());
    }
}
