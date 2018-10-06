package ecse321.t08.rideshare.repository;

import ecse321.t08.rideshare.entity.ATrip;
import ecse321.t08.rideshare.entity.User;
import ecse321.t08.rideshare.utility.rideshareHelper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    UserRepository userRep;
	
	@Transactional
        public ATrip createATrip(
            int status, 
            String cost, 
            int startDate,
            int endDate, 
            String startLocation, 
            String stops, 
            int vehicleId,
            String driverUserName,
            String driverPassword
        ) {
            ATrip aTrip = new ATrip();
            int driverId = userRep.authenticateUser(driverUserName, driverPassword);
            if(driverId == -1) {
                return null;
            }
            User user = userRep.getUser(driverId);
            if(!(user.getRole().equalsIgnoreCase("Driver"))) {
                return null;
            }

            aTrip.setStatus(status);
            aTrip.setCostPerStop(cost);
            aTrip.setStartDate(startDate);
            aTrip.setEndDate(endDate);
            aTrip.setStartLocation(startLocation);
            aTrip.setStops(stops);
            aTrip.setVehicleid(vehicleId);
            em.persist(aTrip);
            return aTrip;
        }

    // If you are an admin, you get to see all the trips
    @Transactional
    public List getUnfilteredTripsList(String username, String password) {
        List<User> user = userRep.findUser(username);
        // Check if user is admin
        if (user.size() == 0 || user.size() > 1 ||!(user.get(0).getRole().equalsIgnoreCase("administrator")) || !(user.get(0).getPassword().equals(password))) {
            return null;
        }
        return em.createQuery("SELECT * FROM ATrip").getResultList();
    }

    @Transactional
    public ATrip getTrip(int id) {
        return em.find(ATrip.class, id);
    }

    // User selects trip and we record it on ATrip
    @Transactional
    public String selectTrip(int aTripID, String username, String password) {
        ATrip trip = getTrip(aTripID);
        List<User> user = userRep.findUser(username);

        // Make sure user and trip exist
        if (user.size() != 1 || trip == null) {
            return "User or trip does not exist.";
        }
        // password is correct
        if (!(user.get(0).getPassword().equals(password))) {
            return "Unable to authenticate user to select trip.";
        }

        if (!("Passenger".equalsIgnoreCase(user.get(0).getRole()))) {
            return "Only passengers can select trips.";
        }

        if (trip.getPassengerid().contains(String.valueOf(user.get(0).getUserID()))) {
            return "User has already selected this trip.";
        }
        if (trip.getPassengerid() == null || trip.getPassengerid().equals("")) {
            trip.setPassengerid(String.valueOf(user.get(0).getUserID()));
        } else {
            trip.setPassengerid(trip.getPassengerid() + ";" + String.valueOf(user.get(0).getUserID()));
        }
        em.merge(trip);
        return ("Passenger " + username + " selected this trip.");
        
    }



    // Cancel a trip if you are a user
    @Transactional
    public String cancelATrip(int aTripID, String username, String password) {
        ATrip trip = getTrip(aTripID);
        List<User> user = userRep.findUser(username);

        // Let's make sure user and trip exist, and user password is correct.

        if (user.size() != 1 || trip == null) {
            return "User or trip does not exist.";
        } else if (!(user.get(0).getPassword().equals(password))) {
            return "Unable to authenticate user to cancel trip.";
        }
        // If user is driver, delete entire trip
        else if ("Driver".equalsIgnoreCase(user.get(0).getRole())) {
            em.remove(trip);
            return "Trip " + aTripID + "deleted";
        }
        // Is user is passenger, just remove passenger ID
        else if ("Passenger".equalsIgnoreCase(user.get(0).getRole())) {
            ArrayList<String> ids = rideshareHelper.tokenizer(trip.getPassengerid(), ";");
            for(String s: ids) {
                if(s.equals(String.valueOf(user.get(0).getUserID()))) {
                    ids.remove(s);
                }
            }
            em.getTransaction().begin();
            trip.setPassengerid(rideshareHelper.concatenator(ids, ";"));
            em.getTransaction().commit();
            return "Passenger " + username + " removed from trip " + aTripID + ".";
        }

        return "Unknown error.";
    }
}
