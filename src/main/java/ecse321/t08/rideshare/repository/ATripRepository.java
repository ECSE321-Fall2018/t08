package ecse321.t08.rideshare.repository;

import ecse321.t08.rideshare.entity.ATrip;
import ecse321.t08.rideshare.entity.User;
import ecse321.t08.rideshare.utility.rideshareHelper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ATripRepository {
	
    @PersistenceContext
	EntityManager em;
	
	@Transactional
        public ATrip createATrip(
            int status, 
            String cost, 
            int startDate,
            int endDate, 
            String startLocation, 
            String stops, 
            int vehicleId
        ) {
            ATrip aTrip = new ATrip();
            
            aTrip.setStatus(status);
            aTrip.setCostPerStop(cost);
            aTrip.setStartDate(startDate);
            aTrip.setEndDate(endDate);
            aTrip.setStartLocation(startLocation);
            aTrip.setStops(stops);
            aTrip.setVehicleId(vehicleId);
            em.persist(aTrip);
            return aTrip;
        }

    // If you are an admin, you get to see all the trips
    @Transactional
    public List getUnfilteredTripsList(String username, String password) {
        User user = em.find(User.class, username);
        // Check if user is admin
        if (
            user == null
            || !(user.getRole().equalsIgnoreCase("administrator"))
            || !(user.getPassword().equals(password))
        ) {
            return null;
        }
        return em.createQuery("SELECT * FROM ATrip").getResultList();
    }

    @Transactional
    public ATrip getTrip(int id) {
        return em.find(ATrip.class, id);
    }

    // Cancel a trip if you are a user
    @Transactional
    public String cancelATrip(int aTripID, String username, String password) {
        ATrip trip = getTrip(aTripID);
        User user = em.find(User.class, username);

        // Let's make sure user and trip exist, and user password is correct.

        if (user == null || trip == null) {
            return null;
        }

        if (!(user.getPassword().equals(password))) {
            return "Unable to authenticate user to cancel trip.";
        }

        // If user is driver, delete entire trip
        if ("Driver".equalsIgnoreCase(user.getRole())) {
            em.remove(trip);
            return "Trip " + aTripID + "deleted";
        }

        // Is user is passenger, just remove passenger ID
        if ("Passenger".equalsIgnoreCase(user.getRole())) {
            ArrayList<String> ids = rideshareHelper.tokenizer(trip.getPassengerId(), ";");
            for(String s: ids) {
                if(s.equals(String.valueOf(user.getUserID()))) {
                    ids.remove(s);
                }
            }
            em.getTransaction().begin();
            trip.setPassengerId(rideshareHelper.concatenator(ids, ";"));
            em.getTransaction().commit();
            return "Passenger " + username + " removed from trip " + aTripID + ".";
        }

        return null;
    }
}
