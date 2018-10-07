package ecse321.t08.rideshare.entity;

import javax.persistence.*;

@Entity
@Table(name = "vehicles")
@NamedQueries({
    @NamedQuery(name = "Vehicle.findAll", query = "SELECT e FROM Vehicle e")
})
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int vehicleId;

    private int driverId;
    private String vehicleType;
    private int nbOfSeats;
    private String model;
    private String colour;

    public Vehicle() {}

    public Vehicle(int driverId, String vehicleType, int nbOfSeats, String model, String colour) {
        this.driverId = driverId;
        this.vehicleType = vehicleType;
        this.nbOfSeats = nbOfSeats;
        this.model = model;
        this.colour = colour;
    }

    @Column(name = "vehicleid")
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Column(name = "driverid")
    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    @Column(name = "seats")
    public int getNbOfSeats() {
        return this.nbOfSeats;
    }

    public void setNbOfSeats(int value) {
        this.nbOfSeats = value;
    }

    @Column(name = "colour")
    public String getColour() {
        return this.colour;
    }

    public void setColour(String value) {
        this.colour = value;
    }

    @Column(name = "model")
    public String getModel() {
        return this.model;
    }

    public void setModel(String value) {
        this.model = value;
    }

    @Column(name = "vehicleType")
    public String getVehicleType() {
        return this.vehicleType;
    }

    public void setVehicleType(String value) {
        this.vehicleType = value;
    }
}
