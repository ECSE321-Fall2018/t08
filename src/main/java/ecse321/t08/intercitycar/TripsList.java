import java.util.Set;

public interface TripsList {
   /**
    * <pre>
    *           1..1     1..1
    * TripsList ------------------------- Administrator
    *           tripsList        &lt;       administrator
    * </pre>
    */
   public void setAdministrator(Administrator value);
   
   public Administrator getAdministrator();
   
   /**
    * <pre>
    *           1..1     1..1
    * TripsList ------------------------- RideShareSystem
    *           tripsList        &gt;       rideShareSystem
    * </pre>
    */
   public void setRideShareSystem(RideShareSystem value);
   
   public RideShareSystem getRideShareSystem();
   
   /**
    * <pre>
    *           1..1     0..*
    * TripsList ------------------------- ATrip
    *           tripsList        &lt;       aTrip
    * </pre>
    */
   public Set<ATrip> getATrip();
   
   public void sortTrips();
   
   public void filterTrips();
   
   }
