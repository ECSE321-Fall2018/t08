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
        public ATrip createATrip(int status, String cost, int startDate, int endDate, String startLocation, String stops, int vehicleId) {
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
<<<<<<< HEAD
    public List getTrips(String username, String password) {
        // Get details about this user
        List<User> userList = entityManager.createNamedQuery("User.findUserName")
        .setParameter("usernameparam", "'%" + username + "%'")
        .getResultList();

        // Check if user is admin
        if ("Admin" == userList.get(0).getRole()) {
            return entityManager.createQuery("SELECT * FROM ATrip;").getResultList();
        } else {
            return null;
        }
=======
    public List getUnfilteredTripsList(String username, String password) {
        User user = em.find(User.class, username);
        if(user == null) {
            return null;
        }
        if(!(user.getRole().equalsIgnoreCase("administrator"))) {
            return null;
        }
        if(!(user.getPassword().equals(password))) {
            return null;
        }
        return em.createQuery("SELECT * FROM ATrip").getResultList();
>>>>>>> 8f5f77accbcdfbbca9ac879933b76db931b5556b
    }

    @Transactional
    public ATrip getTrip(int id) {
        return em.find(ATrip.class, id);
    }

    @Transactional
    public String cancelATrip(int aTripID, String username, String password) {
        ATrip trip = getTrip(aTripID);
        User user = em.find(User.class, username);
        if(user == null || trip == null) {
            return null;
        }

        if(!(user.getPassword().equals(password))) {
            return "Unable to authenticate user to cancel trip.";
        }


        if ("Driver".equalsIgnoreCase(user.getRole())) {
                em.remove(trip);
                return "Trip " + aTripID + "deleted";
        }
        if("Passenger".equalsIgnoreCase(user.getRole())) {
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
