import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;


// It implements Serializable so the objects can be saved to a file
public class Salon implements Serializable {

    private String SalonName;
    private String city;

    private OpeningHours hours; //complex attribute

    private String description = null; //optional attribute

    private Set<String> services = new HashSet<>(); //multi-valued attribute


    private static List<Salon> extent = new ArrayList<>(); //class extent - all salons are stored here

    private static String brandName = "BeautyGroup"; //class attribute

    public Salon(String SalonName) {

        this.SalonName = SalonName;
    }

    public Salon(String SalonName, String city,OpeningHours hours,String Service, String... moreServices) {
        // Validation of the parameters
        if (SalonName == null || SalonName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if (hours == null) {
            throw new IllegalArgumentException("Opening hours cannot be null");
        }
        if (Service == null || Service.trim().isEmpty())
            throw new IllegalArgumentException("At least one service must be provided");

        this.SalonName = SalonName;
        this.city = city;
        this.hours = hours;
        services.add(Service);  // mandatory first service
        for (String s : moreServices) addService(s);
        extent.add(this); // extent - every new salon is added to the extent automatically

    }

    // Getters
    public String getSalonName() {return SalonName;}
    public String getCity() {
        return city;
    }
    public String getDescription() {return description;}
    public static String getBrandName() { return brandName;}


    public static  List<Salon> getExtent() {return Collections.unmodifiableList(extent);}


    public Set<String> getServices() {
        return Collections.unmodifiableSet(services);
    }

    // Derived attribute - working hours per day are not stored in any field
    public int getWorkingHours() {

        return hours.getCloseTime().getHour() - hours.getOpenTime().getHour();
    }

    // Setter
    public void setCity(String city) {
        Objects.requireNonNull(city, "City cannot be null");
        if (city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be empty");
        }
        this.city = city;
    }

    public void setDescription(String description) {this.description = description;}


    public static void setBrandName(String brandName) {
        if (brandName == null || brandName.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand name cannot be empty");
        }
        Salon.brandName = brandName;
    }

    // multi-valued attribute- adding and removing

    // Overload
    public void addService(String service) {
        if (service == null || service.trim().isEmpty()) {
            throw new IllegalArgumentException("Service cannot be null or empty");
        }
        services.add(service);
    }

    // Overload - same method name, but takes any number of services (varargs).
    // calls the first version for every service.
    public void addService(String... newServices) {

        for (String service : newServices) addService(service);
    }

    // removes a service,but minimum 1 service stays.
    public void removeService(String service) {
        if (services.size() == 1) {
            throw new IllegalArgumentException("Salon must have at least one service");
        }
        services.remove(service);
    }


    // class method - searches for all salons in a given city.
    public static List<Salon> searchByCity(String city) {
        List<Salon> results = new ArrayList<>();

        for (Salon s : extent) {
            if (s.getCity().equalsIgnoreCase(city)) {
                results.add(s);
            }
        }

        return results;
    }

    // removes the given salon from the extent.
    public static void removeSalon(Salon s) {
        extent.remove(s);
    }

    // Extent persistency (saving and loading the extent)

    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }


    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Salon>) stream.readObject();
    }

    // Override
    // so a salon is printed inreadable way
    @Override
    public String toString() {
        return String.format(
                "Salon '%s' (%s) in %s, working hours: %s-%s (%dh), services: %s%s",
                SalonName,
                brandName,
                city,
                hours.getOpenTime(),
                hours.getCloseTime(),
                getWorkingHours(),
                services.isEmpty() ? "(no services)" : services,
                description != null ? ", description: " + description : ""
        );


    }
}
