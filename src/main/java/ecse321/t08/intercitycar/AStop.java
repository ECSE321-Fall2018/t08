
public class AStop {
   private String location;
   
   public void setLocation(String value) {
      this.location = value;
   }
   
   public String getLocation() {
      return this.location;
   }
   
   /**
    * <pre>
    *           0..4     1..1
    * AStop ------------------------- ATrip
    *           aStop        &lt;       aTrip
    * </pre>
    */
   private ATrip aTrip;
   
   public void setATrip(ATrip value) {
      this.aTrip = value;
   }
   
   public ATrip getATrip() {
      return this.aTrip;
   }
   
   }
