
public class RideRequest {
   private int requestID;
   
   public void setRequestID(int value) {
      this.requestID = value;
   }
   
   public int getRequestID() {
      return this.requestID;
   }
   
   private int fromUserID;
   
   public void setFromUserID(int value) {
      this.fromUserID = value;
   }
   
   public int getFromUserID() {
      return this.fromUserID;
   }
   
   private int toUserID;
   
   public void setToUserID(int value) {
      this.toUserID = value;
   }
   
   public int getToUserID() {
      return this.toUserID;
   }
   
   private boolean isAccepted;
   
   public void setIsAccepted(boolean value) {
      this.isAccepted = value;
   }
   
   public boolean isIsAccepted() {
      return this.isAccepted;
   }
   
   /**
    * <pre>
    *           0..4     1..1
    * RideRequest ------------------------- ATrip
    *           rideRequest        &lt;       aTrip
    * </pre>
    */
   private ATrip aTrip;
   
   public void setATrip(ATrip value) {
      this.aTrip = value;
   }
   
   public ATrip getATrip() {
      return this.aTrip;
   }
   
   /**
    * <pre>
    *           1..1     1..1
    * RideRequest ------------------------- Passenger
    *           rideRequest        &lt;       passenger
    * </pre>
    */
   private Passenger passenger;
   
   public void setPassenger(Passenger value) {
      this.passenger = value;
   }
   
   public Passenger getPassenger() {
      return this.passenger;
   }
   
   public void acceptRequest() {
      // TODO implement this operation
      throw new UnsupportedOperationException("not implemented");
   }
   
   public void denyRequest() {
      // TODO implement this operation
      throw new UnsupportedOperationException("not implemented");
   }
   
   }
