package ecse321.t08.rideshare.Entity;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;


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

   @Column(name="passengerid")
   public String getPassengerId() {
      return passengerId;
   }

   public void setPassengerId(String passengerId) {
      this.passengerId = passengerId;
   }

   public void setTripID(int value) {
      this.tripID = value;
   }

   @Id
   @Column(name="tripid")
   @GeneratedValue(strategy = GenerationType.AUTO)
   public int getTripID() {
      return this.tripID;
   }

   
   public void setStartDate(int value) {
      this.startDate = value;
   }

   @Column(name="startdate")
   public int getStartDate() {
      return this.startDate;
   }

   @Column(name="enddate")
   public int getEndDate() {
      return endDate;
   }

   public void setEndDate(int endDate) {
      this.endDate = endDate;
   }


   public void setStatus(int value) {
      this.tripStatus = value;
   }

   @Column(name="status")
   public int getStatus() {
      return this.tripStatus;
   }


   public void setStartLocation(String value) {
      this.startLocation = value;
   }

   @Column(name="startlocation")
   public String getStartLocation() {
      return this.startLocation;
   }
   
   public void setStops(String value) {
      this.stops = value;
   }

   @Column(name="getstops")
   public String getStops() {
      return this.stops;
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
