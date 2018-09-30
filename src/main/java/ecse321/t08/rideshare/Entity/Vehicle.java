package ecse321.t08.rideshare.Entity;

@Entity
@Table(name = "vehicles")
public class Vehicle {

	private int nbOfSeats;
	private String colour;
	private String model;
	private ATrip aTrip;
	private Driver driver;

	public void setNbOfSeats(int value) {
		this.nbOfSeats = value;
	}

	public int getNbOfSeats() {
		return this.nbOfSeats;
	}

	public void setColour(String value) {
		this.colour = value;
	}

	public String getColour() {
		return this.colour;
	}

	public void setModel(String value) {
		this.model = value;
	}

	public String getModel() {
		return this.model;
	}

	public void setATrip(ATrip value) {
		this.aTrip = value;
	}

	public ATrip getATrip() {
		return this.aTrip;
	}

	public void setDriver(Driver value) {
		this.driver = value;
	}

	public Driver getDriver() {
		return this.driver;
	}

}
