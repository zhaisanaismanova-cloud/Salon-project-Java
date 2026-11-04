
import java.io.*;
import java.time.LocalTime;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //complex attribute OpeningHours is a separate object used as a complex attribute in Salon.

        OpeningHours glowHours = new OpeningHours(
                LocalTime.of(9, 0),
                LocalTime.of(18, 0)
        );

        // Extent - the new salon is automatically added to the extent.
        Salon s1 = new Salon("Glow", "Warsaw", glowHours, "Haircut");
        s1.setDescription("Cozy and nice"); // Optional attribute - description is set.

        // Optional attribute - s2 has no description (its value remains null).
        Salon s2 = new Salon("F4", "Gdansk",
                new OpeningHours(LocalTime.of(7, 0), LocalTime.of(17, 0)),
                "Manicure");

        Salon s3 = new Salon("U Hani", "Poznan",
                new OpeningHours(LocalTime.of(10, 0), LocalTime.of(19, 0)),
                "Pedicure");

        Salon s4 = new Salon("Fresh", "Krakow",
                new OpeningHours(LocalTime.of(8, 0), LocalTime.of(16, 0)),
                "SPA");
        s4.setDescription("Luxury with spa services");

        Salon s5 = new Salon("Shirley", "Lodz",
                new OpeningHours(LocalTime.of(9, 0), LocalTime.of(19, 0)),
                "Waxing");
        Salon s6 = new Salon("Lalka","Warsaw", new OpeningHours(LocalTime.of(9, 0), LocalTime.of(19, 0)), "Haircut" );
        s6.setDescription("Located in city center");
        Salon s7 = new Salon("kBeauty","Lodz", new OpeningHours(LocalTime.of(9, 0), LocalTime.of(19, 0)), "Trimming and waxing" );



        System.out.println("\n=== SEARCH BY CITY: WARSAW ===");

        List<Salon> foundSalons = Salon.searchByCity("Warsaw"); //class method - search for salons by city.

        for (Salon salon : foundSalons) {
            System.out.println(salon);
        }

        System.out.println("Salons in Warsaw:");



        // Extent - returns all salons currently stored
        System.out.println("\nNumber of salons in extent: "
                + Salon.getExtent().size());

        // override

        System.out.println("\nOverridden toString(): " + s1);


        // Derived attribute - working hours are calculated from opening and closing times.
        System.out.println("\nGlow works " + s1.getWorkingHours() + "h a day");

        // Complex attribute - changing the OpeningHours object of the salon.
        glowHours.setCloseTime(LocalTime.of(20, 0));

        // Derived attribute - the result changes automatically because it is calculated.
        System.out.println("Glow works " + s1.getWorkingHours()
                + "h a day after the change");


        // Multi-valued attribute + Overload - adds multiple services using varargs.
        s1.addService("Facial", "Bucal removal");
        s3.addService("Haircut", "Massage");

        // Multi-valued attribute + Overload - adds one service using the single-parameter version.
        s2.addService("Pedicure");

        // Multi-valued attribute - removes a service from the collection.
        s2.removeService("Manicure");

        try {
            // Multi-valued attribute - the last service cannot be removed.
            s5.removeService("Waxing");
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }



        // Class method - searches for salons in Lodz after modifying services.
        System.out.println("\nSalons in Lodz:");

        List<Salon> lodzSalons = Salon.searchByCity("Lodz");

        for (Salon salon : lodzSalons) {
            System.out.println(salon);
        }


        // Class attribute - brandName is shared by all Salon objects.
        System.out.println("\nBrand: " + Salon.getBrandName());

        // Class attribute - changing the static value affects all salons.
        Salon.setBrandName("BeautyGroup Premium");

        System.out.println("Salon s1 after brand change: " + s1);

        // Class method - removes s2 from the class extent.
        System.out.println("\nRemoving one salon...");
        Salon.removeSalon(s2);

        // Class method - searches the extent again after removal.
        System.out.println("\nSalons in Warsaw after removal:");

        List<Salon> warsawSalons = Salon.searchByCity("Warsaw");

        for (Salon salon : warsawSalons) {
            System.out.println(salon);
        }


        // Extent persistency - saving the complete extent to a file.
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("salons.dat")
        );
        Salon.writeExtent(out);
        out.close();

        // Removing s4 after saving allows us to demonstrate loading.
        Salon.removeSalon(s4);

        System.out.println("\nNumber of salons before loading: "
                + Salon.getExtent().size());

        // Extent persistency - loading the previously saved extent from a file.
        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("salons.dat")
        );
        Salon.readExtent(in);
        in.close();

        System.out.println("\nNumber of salons after loading: "
                + Salon.getExtent().size());

        // Class method - search after restoring the extent from the file.
        System.out.println("\nSalons in Krakow after loading:");

        List<Salon> krakowSalons = Salon.searchByCity("Krakow");

        for (Salon salon : krakowSalons) {
            System.out.println(salon);
        }

        // Class attribute - static brandName is not part of the serialized extent.
        System.out.println("\nBrand: " + Salon.getBrandName());
    }
}
