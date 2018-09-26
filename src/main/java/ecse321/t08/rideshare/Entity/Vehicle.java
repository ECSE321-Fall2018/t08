package ecse321.t08.rideshare.Entity;

public class Vehicle {
   private int nbOfSeats;
   
   public void setNbOfSeats(int value) {
      this.nbOfSeats = value;
   }
   
   public int getNbOfSeats() {
      return this.nbOfSeats;
   }
   
   private String colour;
   
   public void setColour(String value) {
      this.colour = value;
   }
   
   public String getColour() {
      return this.colour;
   }
   
   private String model;
   
   public void setModel(String value) {
      this.model = value;
   }
   
   public String getModel() {
      return this.model;
   }
   
   /**
    * <pre>
    *           1..1     1..1
    * Vehicle ------------------------- ATrip
    *           vehicle        &lt;       aTrip
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
    * Vehicle ------------------------- Driver
    *           vehicle        &lt;       driver
    * </pre>
    */
   private Driver driver;
   
   public void setDriver(Driver value) {
      this.driver = value;
   }
   
   public Driver getDriver() {
      return this.driver;
   }
   
   }
