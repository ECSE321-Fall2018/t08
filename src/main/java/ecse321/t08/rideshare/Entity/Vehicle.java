package ecse321.t08.rideshare.Entity;

import javax.persistence.*;

@Entity
@Table(name="vehicle")
public class Vehicle {


	private int vehicleId;
	private int nbOfSeats;
	private String colour;
	private String model;
	private int driverId;
	private String vehicleType;

	@Column(name="driverid")
	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public void setNbOfSeats(int value) {
		this.nbOfSeats = value;
	}

	@Column(name="seats")
	public int getNbOfSeats() {
		return this.nbOfSeats;
	}

	public void setColour(String value) {
		this.colour = value;
	}

	@Column(name="colour")
	public String getColour() {
		return this.colour;
	}

	public void setModel(String value) {
		this.model = value;
	}

	@Column(name="model")
	public String getModel() {
		return this.model;
	}

	public void setVehicleType(String value){
		this.vehicleType = value;
	}

	@Column(name="vehicleType")
	public String getVehicleType(){
		return this.vehicleType;
	}

	@Id
	@Column(name="vehicleid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
}
