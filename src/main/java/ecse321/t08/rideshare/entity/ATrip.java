package ecse321.t08.rideshare.entity;

import javax.persistence.*;


@Entity
@Table(name="ATrip")
public class ATrip {

   private int tripID;
   private int tripStatus; //0 for ongoing, 1 planned, 2 for completed

   private String costPerStop; //Contains all costs per stop, in order, separated by delimiter ';'
   private int startDate; //Implemented as Unix Time Stamp
   private int endDate;
   private String startLocation;
   private String stops; //Contains all stops, separated by delimiter ';'
   private int vehicleId;
   private String passengerId; //Contains all passenger ids, delimiter is ';'

   public ATrip() {
   }

   public ATrip(int tripID, int tripStatus, String costPerStop, int startDate, int endDate, String startLocation, String stops, int vehicleId, String passengerId) {
      this.tripID = tripID;
      this.tripStatus = tripStatus;
      this.costPerStop = costPerStop;
      this.startDate = startDate;
      this.endDate = endDate;
      this.startLocation = startLocation;
      this.stops = stops;
      this.vehicleId = vehicleId;
      this.passengerId = passengerId;
   }

   @Column(name="passengerid")
   public String getPassengerId() {
      return passengerId;
   }

   public void setPassengerId(String passengerId) {
      this.passengerId = passengerId;
   }

   @Id
   @Column(name="tripid")
   @GeneratedValue(strategy = GenerationType.AUTO)
   public int getTripID() {
      return this.tripID;
   }

   public void setTripID(int value) {
      this.tripID = value;
   }


   @Column(name="startdate")
   public int getStartDate() {
      return this.startDate;
   }

   public void setStartDate(int value) {
      this.startDate = value;
   }

   @Column(name="enddate")
   public int getEndDate() {
      return endDate;
   }

   public void setEndDate(int endDate) {
      this.endDate = endDate;
   }

   @Column(name="status")
   public int getStatus() {
      return this.tripStatus;
   }

   public void setStatus(int value) {
      this.tripStatus = value;
   }

   @Column(name="startlocation")
   public String getStartLocation() {
      return this.startLocation;
   }

   public void setStartLocation(String value) {
      this.startLocation = value;
   }

   @Column(name="getstops")
   public String getStops() {
      return this.stops;
   }

   public void setStops(String value) {
      this.stops = value;
   }

   @Column(name="cost")
   public String getCostPerStop() {
      return costPerStop;
   }

   public void setCostPerStop(String costPerStop) {
      this.costPerStop = costPerStop;
   }

   @Column(name="vehicleid")
   public int getVehicleId() {
      return vehicleId;
   }

   public void setVehicleId(int vehicleId) {
      this.vehicleId = vehicleId;
   }
}
