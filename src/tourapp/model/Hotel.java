package tourapp.model;

public class Hotel {

    private String name;
    private int stars;
    private double rating;

    public Hotel(String name, int stars, double rating) {
        this.name = name;
        this.stars = stars;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getStars() {
        return stars;
    }

    public double getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return name + " (" + stars + "*, rating " + rating + ")";
    }
}
