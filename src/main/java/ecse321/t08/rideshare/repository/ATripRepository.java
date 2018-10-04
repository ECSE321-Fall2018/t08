package ecse321.t08.rideshare.repository;

import ecse321.t08.rideshare.entity.ATrip;
import ecse321.t08.rideshare.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ATripRepository {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
        public ATrip createATrip(int status, String cost, int startDate, int endDate, String startLocation, String stops, int vehicleId) {
            ATrip aTrip = new ATrip();
            aTrip.setStatus(status);
            aTrip.setCostPerStop(cost);
            aTrip.setStartDate(startDate);
            aTrip.setEndDate(endDate);
            aTrip.setStartLocation(startLocation);
            aTrip.setStops(stops);
            aTrip.setVehicleId(vehicleId);
            entityManager.persist(aTrip);
            return aTrip;
        }

    @Transactional
    public ATrip getTrip(int id) {
        return entityManager.find(ATrip.class, id);
    }

    @Transactional
    public void cancelATrip(int aTripID, int userID) {
        ATrip trip = getTrip(aTripID);
        if ("Driver" == entityManager.find(User.class, userID).getRole()) {
                entityManager.remove(trip);
        }
        else if("Passenger" == entityManager.find(User.class, userID).getRole()) {
            //remove values associated with passenger (cost, stop and passenger ID)
        }
    }
}
