package ecse321.t08.rideshare.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ecse321.t08.rideshare.Entity.Vehicle;

@Repository
public class VehicleRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	public ATrip createVehicle(int nbOfSeats, String colour, String model) {
        Vehicle aVehicle = new Vehicle();
        aVehicle.setNbOfSeats(nbOfSeats);
        aVehicle.setColour(colour);
        aVehicle.setModel(model);
        entityManager.persist(aVehicle);
        return aVehicle;
	}
}
