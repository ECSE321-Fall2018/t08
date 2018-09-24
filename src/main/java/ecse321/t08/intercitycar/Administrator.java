
public class Administrator extends UserRole {
   /**
    * <pre>
    *           1..1     1..1
    * Administrator ------------------------- TripsList
    *           administrator        &gt;       tripsList
    * </pre>
    */
   private TripsList tripsList;
   
   public void setTripsList(TripsList value) {
      this.tripsList = value;
   }
   
   public TripsList getTripsList() {
      return this.tripsList;
   }
   
   public void checkstatus() {
      // TODO implement this operation
      throw new UnsupportedOperationException("not implemented");
   }
   
   public void listTop() {
      // TODO implement this operation
      throw new UnsupportedOperationException("not implemented");
   }
   
   }
