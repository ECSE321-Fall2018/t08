package ecse321.t08.rideshare.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ecse321.t08.rideshare.Entity.Vehicle;

@Repository
public class VehicleRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	public Vehicle createVehicle(int driverId, int nbOfSeats, String colour, String model) {
        Vehicle aVehicle = new Vehicle();
        aVehicle.setDriverId(driverId);
        aVehicle.setNbOfSeats(nbOfSeats);
        aVehicle.setColour(colour);
        aVehicle.setModel(model);
        entityManager.persist(aVehicle);
        return aVehicle;
	}

    @Transactional
    public Vehicle getVehicle(int id) {
        Vehicle vehicle = entityManager.find(Vehicle.class, id);

        return vehicle;
    }
}
