package ecse321.t08.rideshare.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ecse321.t08.rideshare.Entity.ATrip;

@Repository
public class ATripRepository {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	public ATrip createATrip(int tripID, double cost, int date, String startLocation, String endLocation) {
        ATrip aTrip = new ATrip();
        aTrip.setTripID(tripID);
        aTrip.setCost(cost);
        aTrip.setDate(date);
        aTrip.setStartLocation(startLocation);
        aTrip.setEndLocation(endLocation);
        entityManager.persist(aTrip);
        return aTrip;
	}
}
