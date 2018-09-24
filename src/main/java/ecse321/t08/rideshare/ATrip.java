import java.util.Set;
import java.util.HashSet;



public class ATrip {
/**
    * <pre>
    *           0..*     1..1
    * ATrip ------------------------- TripsList
    *           aTrip        &gt;       tripsList
    * </pre>
    */
   private TripsList tripsList;
   private int tripStatus = 0; //0 for ongoing, 1 planned, 2 for completed
   
   public void setTripsList(TripsList value) {
      this.tripsList = value;
   }
   
   public TripsList getTripsList() {
      return this.tripsList;
   }
   
   private int tripID;
   
   public void setTripID(int value) {
      this.tripID = value;
   }
   
   public int getTripID() {
      return this.tripID;
   }
   
   private double cost;
   
   public void setCost(double value) {
      this.cost = value;
   }
   
   public double getCost() {
      return this.cost;
   }
   
   private int date;
   
   public void setDate(int value) {
      this.date = value;
   }
   
   public int getDate() {
      return this.date;
   }
   
   public void setStatus(int value) {
      this.tripStatus = value;
   }
   
   public int getStatus() {
      return this.tripStatus;
   }
   
   private String startLocation;
   
   public void setStartLocation(String value) {
      this.startLocation = value;
   }
   
   public String getStartLocation() {
      return this.startLocation;
   }
   
   private String endLocation;
   
   public void setEndLocation(String value) {
      this.endLocation = value;
   }
   
   public String getEndLocation() {
      return this.endLocation;
   }
   
   /**
    * <pre>
    *           1..1     0..4
    * ATrip ------------------------- AStop
    *           aTrip        &gt;       aStop
    * </pre>
    */
   private Set<AStop> aStop;
   
   public Set<AStop> getAStop() {
      if (this.aStop == null) {
         this.aStop = new HashSet<AStop>();
      }
      return this.aStop;
   }
   
   /**
    * <pre>
    *           1..1     1..1
    * ATrip ------------------------- Vehicle
    *           aTrip        &gt;       vehicle
    * </pre>
    */
   private Vehicle vehicle;
   
   public void setVehicle(Vehicle value) {
      this.vehicle = value;
   }
   
   public Vehicle getVehicle() {
      return this.vehicle;
   }
   
   /**
    * <pre>
    *           1..1     0..4
    * ATrip ------------------------- RideRequest
    *           aTrip        &gt;       rideRequest
    * </pre>
    */
   private Set<RideRequest> rideRequest;
   
   public Set<RideRequest> getRideRequest() {
      if (this.rideRequest == null) {
         this.rideRequest = new HashSet<RideRequest>();
      }
      return this.rideRequest;
   }
   
   }
