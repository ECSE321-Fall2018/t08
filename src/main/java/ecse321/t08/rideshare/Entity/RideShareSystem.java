package ecse321.t08.rideshare.Entity;

public class RideShareSystem {
   /**
    * <pre>
    *           1..1     1..1
    * RideShareSystem ------------------------- TripsList
    *           rideShareSystem        &lt;       tripsList
    * </pre>
    */
   private TripsList tripsList;
   
   public void setTripsList(TripsList value) {
      this.tripsList = value;
   }
   
   public TripsList getTripsList() {
      return this.tripsList;
   }
   
   }
