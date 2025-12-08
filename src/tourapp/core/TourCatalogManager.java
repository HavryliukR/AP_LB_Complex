package tourapp.core;

import tourapp.logging.LoggingConfig;
import tourapp.model.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TourCatalogManager {

    private static final Logger LOGGER = LoggingConfig.getLogger(TourCatalogManager.class);

    private final List<Tour> tours = new ArrayList<>();
    private final List<Tour> favoriteTours = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    private long nextTourId = 1;
    private long nextBookingId = 1;

    public List<Tour> getAllTours() {
        return Collections.unmodifiableList(tours);
    }

    public List<Tour> getFavoriteTours() {
        return Collections.unmodifiableList(favoriteTours);
    }

    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }

    public Tour addTour(Tour tour) {
        long id = nextTourId++;
        tour.setId(id);
        tours.add(tour);
        LOGGER.info("Added tour with id=" + id + ", name=" + tour.getName());
        return tour;
    }

    public boolean updateTour(long id, Tour updatedTour) {
        Tour existing = findTourById(id);
        if (existing == null) {
            LOGGER.warning("Attempt to update non-existing tour id=" + id);
            return false;
        }
        existing.updateFrom(updatedTour);
        LOGGER.info("Updated tour id=" + id);
        return true;
    }

    public boolean deleteTour(long id) {
        Iterator<Tour> iterator = tours.iterator();
        boolean removed = false;
        while (iterator.hasNext()) {
            Tour tour = iterator.next();
            if (tour.getId() == id) {
                iterator.remove();
                removed = true;
                break;
            }
        }
        if (removed) {
            favoriteTours.removeIf(t -> t.getId() == id);
            bookings.removeIf(b -> b.getTour().getId() == id);
            LOGGER.info("Deleted tour id=" + id);
        } else {
            LOGGER.warning("Attempt to delete non-existing tour id=" + id);
        }
        return removed;
    }

    public Tour findTourById(long id) {
        for (Tour tour : tours) {
            if (tour.getId() == id) {
                return tour;
            }
        }
        return null;
    }

    public void viewAllTours() {
        printTours(tours);
    }

    private void printTours(List<Tour> list) {
        if (list.isEmpty()) {
            System.out.println("No tours found.");
            return;
        }
        for (Tour tour : list) {
            System.out.println(tour);
        }
    }

    public List<Tour> findTours(
            TourType type,
            String country,
            TransportType transportType,
            FoodType foodType,
            Integer minDays,
            Integer maxDays,
            Double minPrice,
            Double maxPrice
    ) {
        List<Tour> result = new ArrayList<>();
        for (Tour tour : tours) {
            if (type != null && tour.getType() != type) {
                continue;
            }
            if (country != null && tour.getLocation() != null) {
                if (!tour.getLocation().getCountry().equalsIgnoreCase(country)) {
                    continue;
                }
            } else if (country != null) {
                continue;
            }
            if (transportType != null && tour.getTransportType() != transportType) {
                continue;
            }
            if (foodType != null && tour.getFoodType() != foodType) {
                continue;
            }
            if (minDays != null && tour.getDays() < minDays) {
                continue;
            }
            if (maxDays != null && tour.getDays() > maxDays) {
                continue;
            }
            if (minPrice != null && tour.getPrice() < minPrice) {
                continue;
            }
            if (maxPrice != null && tour.getPrice() > maxPrice) {
                continue;
            }
            result.add(tour);
        }
        return result;
    }

    public void searchAndFilterTours(TourType type,
                                     String country,
                                     TransportType transportType,
                                     FoodType foodType,
                                     Integer minDays,
                                     Integer maxDays,
                                     Double minPrice,
                                     Double maxPrice) {
        List<Tour> result = findTours(type, country, transportType, foodType,
                minDays, maxDays, minPrice, maxPrice);
        printTours(result);
    }

    public List<Tour> getToursSortedBy(SortField field, boolean ascending) {
        List<Tour> copy = new ArrayList<>(tours);
        Comparator<Tour> comparator;
        switch (field) {
            case PRICE:
                comparator = Comparator.comparingDouble(Tour::getPrice);
                break;
            case DAYS:
                comparator = Comparator.comparingInt(Tour::getDays);
                break;
            case RATING:
                comparator = Comparator.comparingDouble(Tour::getRating);
                break;
            default:
                comparator = Comparator.comparingLong(Tour::getId);
        }
        if (!ascending) {
            comparator = comparator.reversed();
        }
        copy.sort(comparator);
        return copy;
    }

    public void sortTours(SortField field, boolean ascending) {
        List<Tour> sorted = getToursSortedBy(field, ascending);
        printTours(sorted);
    }

    public boolean addToFavorites(long tourId) {
        Tour tour = findTourById(tourId);
        if (tour == null) {
            LOGGER.warning("Attempt to add non-existing tour to favorites, id=" + tourId);
            return false;
        }
        if (favoriteTours.stream().anyMatch(t -> t.getId() == tourId)) {
            LOGGER.info("Tour id=" + tourId + " is already in favorites");
            return false;
        }
        favoriteTours.add(tour);
        LOGGER.info("Added tour id=" + tourId + " to favorites");
        return true;
    }

    public boolean removeFromFavorites(long tourId) {
        boolean removed = favoriteTours.removeIf(t -> t.getId() == tourId);
        if (removed) {
            LOGGER.info("Removed tour id=" + tourId + " from favorites");
        } else {
            LOGGER.warning("Attempt to remove non-existing favorite tour id=" + tourId);
        }
        return removed;
    }

    public void manageFavorites() {
        LOGGER.info("Managing favorites, count=" + favoriteTours.size());
        printTours(favoriteTours);
    }

    public Booking createBooking(long tourId, Customer customer, int persons) {
        Tour tour = findTourById(tourId);
        if (tour == null) {
            LOGGER.warning("Attempt to create booking for non-existing tour id=" + tourId);
            return null;
        }
        Booking booking = new Booking(nextBookingId++, tour, customer, persons, BookingStatus.NEW);
        bookings.add(booking);
        LOGGER.info("Created booking id=" + booking.getId() + " for tour id=" + tourId);
        return booking;
    }

    public boolean cancelBooking(long bookingId) {
        for (Booking booking : bookings) {
            if (booking.getId() == bookingId) {
                booking.setStatus(BookingStatus.CANCELED);
                LOGGER.info("Canceled booking id=" + bookingId);
                return true;
            }
        }
        LOGGER.warning("Attempt to cancel non-existing booking id=" + bookingId);
        return false;
    }

    public void manageBookings() {
        LOGGER.info("Managing bookings, count=" + bookings.size());
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    public void loadFromFile(String path) {
        LOGGER.info("Loading tours from file: " + path);
        Path file = Path.of(path);
        if (!Files.exists(file)) {
            System.out.println("File does not exist: " + path);
            return;
        }

        List<Tour> loaded = new ArrayList<>();
        long maxId = 0;

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length < 14) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
                try {
                    long id = Long.parseLong(parts[0]);
                    String name = parts[1];
                    TourType type = TourType.valueOf(parts[2]);
                    String country = parts[3];
                    String city = parts[4];
                    String hotelName = parts[5];
                    int stars = Integer.parseInt(parts[6]);
                    double hotelRating = Double.parseDouble(parts[7]);
                    TransportType transportType = TransportType.valueOf(parts[8]);
                    FoodType foodType = FoodType.valueOf(parts[9]);
                    int days = Integer.parseInt(parts[10]);
                    double price = Double.parseDouble(parts[11]);
                    double rating = Double.parseDouble(parts[12]);
                    String description = parts[13];

                    Location location = new Location(country, city);
                    Hotel hotel = new Hotel(hotelName, stars, hotelRating);
                    Tour tour = new Tour(id, name, type, location, hotel,
                            transportType, foodType, days, price, rating, description);

                    loaded.add(tour);
                    if (id > maxId) {
                        maxId = id;
                    }
                } catch (Exception e) {
                    System.out.println("Skipping invalid line (parse error): " + line);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load tours from file: " + path, e);
            System.out.println("Error while reading file: " + e.getMessage());
            return;
        }

        tours.clear();
        tours.addAll(loaded);
        favoriteTours.clear();
        bookings.clear();

        nextTourId = maxId + 1;
        LOGGER.info("Catalog loaded successfully from file: " + path + ", tours count=" + tours.size());
        System.out.println("Catalog loaded successfully. Tours count: " + tours.size());
    }

    public void saveToFile(String path) {
        LOGGER.info("Saving tours to file: " + path);
        Path file = Path.of(path);

        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (Tour tour : tours) {
                String line = String.join("|",
                        String.valueOf(tour.getId()),
                        escape(tour.getName()),
                        tour.getType().name(),
                        escape(tour.getLocation().getCountry()),
                        escape(tour.getLocation().getCity()),
                        escape(tour.getHotel().getName()),
                        String.valueOf(tour.getHotel().getStars()),
                        String.valueOf(tour.getHotel().getRating()),
                        tour.getTransportType().name(),
                        tour.getFoodType().name(),
                        String.valueOf(tour.getDays()),
                        String.valueOf(tour.getPrice()),
                        String.valueOf(tour.getRating()),
                        escape(tour.getDescription())
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save tours to file: " + path, e);
            System.out.println("Error while writing file: " + e.getMessage());
            return;
        }

        LOGGER.info("Catalog saved successfully to: " + path);
        System.out.println("Catalog saved successfully to: " + path);
    }

    private String escape(String value) {
        return value == null ? "" : value.replace("|", "/");
    }
}
