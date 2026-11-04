import java.io.Serializable;
import java.time.LocalTime;


public class OpeningHours implements Serializable {
    private LocalTime openTime;
    private LocalTime closeTime;


    // Constructor
    public OpeningHours(LocalTime openTime, LocalTime closeTime) {
        if (openTime == null || closeTime == null) {
            throw new IllegalArgumentException("Opening and closing times cannot be null");
        }
        if (closeTime.isBefore(openTime)) {
            throw new IllegalArgumentException("Closing time cannot be before opening time");
        }

        this.openTime = openTime;
        this.closeTime = closeTime;
    }
    public LocalTime getOpenTime() {
        return openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }


    // Setter with validation
    public void setOpenTime(LocalTime openTime) {
        if (openTime == null) {
            throw new IllegalArgumentException("Opening time cannot be null");
        }
        if (closeTime != null && openTime.isAfter(closeTime)) {
            throw new IllegalArgumentException("Opening time cannot be after closing time");
        }
        this.openTime = openTime;
    }

    // Setter with validation
    public void setCloseTime(LocalTime endTime) {
        if (endTime == null) {
            throw new IllegalArgumentException("Closing time cannot be null");
        }
        if (openTime != null && endTime.isBefore(openTime)) {
            throw new IllegalArgumentException("Closing time cannot be before opening time");
        }
        this.closeTime = endTime;
    }

    // Override
    @Override
    public String toString() {
        return "OpeningHours{" + "openTime=" + openTime + ", closeTime=" + closeTime + '}';


    }
}
