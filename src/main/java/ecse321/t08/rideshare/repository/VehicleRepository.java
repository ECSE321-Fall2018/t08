package ecse321.t08.rideshare.repository;

import ecse321.t08.rideshare.entity.Vehicle;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class VehicleRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	public Vehicle createVehicle(int driverId, int nbOfSeats, String colour, String model, String vehicleType) {
        Vehicle aVehicle = new Vehicle();
        aVehicle.setDriverId(driverId);
        aVehicle.setNbOfSeats(nbOfSeats);
        aVehicle.setColour(colour);
        aVehicle.setModel(model);
        aVehicle.setVehicleType(vehicleType);
        entityManager.persist(aVehicle);
        return aVehicle;
	}

    @Transactional
    public Vehicle getVehicle(int id) {
        return entityManager.find(Vehicle.class, id);
    }
}
