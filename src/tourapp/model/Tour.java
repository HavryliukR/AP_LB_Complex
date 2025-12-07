package tourapp.model;

public class Tour {

    private long id;
    private String name;
    private TourType type;
    private Location location;
    private Hotel hotel;
    private TransportType transportType;
    private FoodType foodType;
    private int days;
    private double price;
    private double rating;
    private String description;

    public Tour(long id,
                String name,
                TourType type,
                Location location,
                Hotel hotel,
                TransportType transportType,
                FoodType foodType,
                int days,
                double price,
                double rating,
                String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.hotel = hotel;
        this.transportType = transportType;
        this.foodType = foodType;
        this.days = days;
        this.price = price;
        this.rating = rating;
        this.description = description;
    }

    public Tour(String name,
                TourType type,
                Location location,
                Hotel hotel,
                TransportType transportType,
                FoodType foodType,
                int days,
                double price,
                double rating,
                String description) {
        this(0L, name, type, location, hotel, transportType, foodType, days, price, rating, description);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TourType getType() {
        return type;
    }

    public void setType(TourType type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public int getDays() {
        return days;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void updateFrom(Tour source) {
        this.name = source.name;
        this.type = source.type;
        this.location = source.location;
        this.hotel = source.hotel;
        this.transportType = source.transportType;
        this.foodType = source.foodType;
        this.days = source.days;
        this.price = source.price;
        this.rating = source.rating;
        this.description = source.description;
    }

    @Override
    public String toString() {
        return "Tour #" + id +
                " [" + name + "] " +
                "(" + type + "), " +
                days + " days, " +
                "price " + price + ", rating " + rating + ", " +
                "location: " + (location != null ? location : "N/A") + ", " +
                "hotel: " + (hotel != null ? hotel : "N/A") + ", " +
                "transport: " + transportType + ", food: " + foodType;
    }
}
