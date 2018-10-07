package ecse321.t08.rideshare.entity;

import javax.persistence.*;

@Entity
@Table(name = "ATrip")
public class ATrip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tripid;

    private int tripStatus; // 0 for ongoing, 1 for planned, 2 for completed
    private String costPerStop; // Contains all costs per stop, in order, separated by delimiter ';'
    private int startDate; // Implemented as Unix Time Stamp
    private int endDate;
    private String startLocation;
    private String stops; // Contains all stops, separated by delimiter ';'
    private int vehicleid;
    private String passengerid; // Contains all passenger ids, separated by delimiter ';'
    private int driverid;

    public ATrip() {}

    public ATrip(
        int tripStatus,
        String costPerStop,
        int startDate,
        int endDate,
        String startLocation,
        String stops,
        int vehicleid,
        String passengerid,
        int driverid
    ) {
        this.tripStatus = tripStatus;
        this.costPerStop = costPerStop;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startLocation = startLocation;
        this.stops = stops;
        this.vehicleid = vehicleid;
        this.passengerid = passengerid;
        this.driverid = driverid;
    }

    @Column(name = "passengerid")
    public String getPassengerid() {
        return passengerid;
    }

    public void setPassengerid(String passengerid) {
        this.passengerid = passengerid;
    }

    @Column(name = "tripid")
    public int getTripId() {
        return this.tripid;
    }

    public void setTripId(int value) {
        this.tripid = value;
    }

    @Column(name = "startdate")
    public int getStartDate() {
        return this.startDate;
    }

    public void setStartDate(int value) {
        this.startDate = value;
    }

    @Column(name = "enddate")
    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    @Column(name = "status")
    public int getStatus() {
        return this.tripStatus;
    }

    public void setStatus(int value) {
        this.tripStatus = value;
    }

    @Column(name = "startlocation")
    public String getStartLocation() {
        return this.startLocation;
    }

    public void setStartLocation(String value) {
        this.startLocation = value;
    }

    @Column(name = "getstops")
    public String getStops() {
        return this.stops;
    }

    public void setStops(String value) {
        this.stops = value;
    }

    @Column(name = "cost")
    public String getCostPerStop() {
        return costPerStop;
    }

    public void setCostPerStop(String costPerStop) {
        this.costPerStop = costPerStop;
    }

    @Column(name = "vehicleid")
    public int getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(int vehicleid) {
        this.vehicleid = vehicleid;
    }

    @Column(name = "driverid")
    public int getDriverid() {
        return driverid;
    }

    public void setDriverid(int driverid) {
        this.driverid = driverid;
    }
}
