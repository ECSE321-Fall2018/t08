package ecse321.t08.rideshare.Entity;

public class Driver extends UserRole {
   /**
    * <pre>
    *           1..1     1..1
    * Driver ------------------------- Vehicle
    *           driver        &gt;       vehicle
    * </pre>
    */
   private Vehicle vehicle;
   
   public void setVehicle(Vehicle value) {
      this.vehicle = value;
   }
   
   public Vehicle getVehicle() {
      return this.vehicle;
   }
   
   public void postTrip() {
      // TODO implement this operation
      throw new UnsupportedOperationException("not implemented");
   }
   
   public void cancelTrip() {
      // TODO implement this operation
      throw new UnsupportedOperationException("not implemented");
   }
   
   public void modifyTrip() {
      // TODO implement this operation
      throw new UnsupportedOperationException("not implemented");
   }
   
   }
