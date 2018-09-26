package ecse321.t08.rideshare.Entity;

public class Passenger extends UserRole {
   private int ridesTaken;
   
   public void setRidesTaken(int value) {
      this.ridesTaken = value;
   }
   
   public int getRidesTaken() {
      return this.ridesTaken;
   }
   
   /**
    * <pre>
    *           1..1     1..1
    * Passenger ------------------------- RideRequest
    *           passenger        &gt;       rideRequest
    * </pre>
    */
   private RideRequest rideRequest;
   
   public void setRideRequest(RideRequest value) {
      this.rideRequest = value;
   }
   
   public RideRequest getRideRequest() {
      return this.rideRequest;
   }
   
   }
