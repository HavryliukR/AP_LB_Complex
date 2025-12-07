package tourapp.model;

public class Booking {

    private long id;
    private Tour tour;
    private Customer customer;
    private int persons;
    private BookingStatus status;

    public Booking(long id, Tour tour, Customer customer, int persons, BookingStatus status) {
        this.id = id;
        this.tour = tour;
        this.customer = customer;
        this.persons = persons;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public Tour getTour() {
        return tour;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getPersons() {
        return persons;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Booking #" + id +
                " [tour #" + tour.getId() + " - " + tour.getName() + "], " +
                "customer: " + customer.getFullName() +
                ", persons: " + persons +
                ", status: " + status;
    }
}
