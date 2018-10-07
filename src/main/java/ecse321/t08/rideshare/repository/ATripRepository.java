package ecse321.t08.rideshare.repository;

import ecse321.t08.rideshare.entity.ATrip;
import ecse321.t08.rideshare.entity.User;
import ecse321.t08.rideshare.entity.Vehicle;
import ecse321.t08.rideshare.utility.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ATripRepository {
    @PersistenceContext
    EntityManager em;

    @Autowired
    UserRepository userRep;

    @Autowired
    VehicleRepository vehicleRep;

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
        // Check for username, password, user is driver
        int driverId = userRep.authorizeUser(driverUserName, driverPassword, "Driver");
        if (driverId == -1) {
            return null;
        }
        
        User user = userRep.getUser(driverId);
        user.setTripNumber(user.getTripNumber() + 1);
        em.merge(user);
        
        ATrip aTrip = new ATrip(
            status,
            cost,
            startDate,
            endDate,
            startLocation,
            stops,
            vehicleId,
            "",
            driverId
        );
        em.persist(aTrip);

        return aTrip;
    }

    // If you are an admin, you get to see all the trips
    @Transactional
    public List getUnfilteredTripsList(String username, String password) {
        List<User> user = userRep.findUser(username);
        // Check if user is admin
        if (userRep.authorizeUser(username, password, "Administrator") == -1) {
            return new ArrayList<User>();
        } else {
            return em.createQuery("SELECT * FROM ATrip").getResultList();
        }
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
        
        if (user.size() != 1 || trip == null) {
            return "User or trip does not exist.";
        }
        // Make sure username and password are correct
        else if (!(user.get(0).getPassword().equals(password))) {
            return "Unable to authenticate user to select trip.";
        }
        else if (!("Passenger".equalsIgnoreCase(user.get(0).getRole()))) {
            return "Only passengers can select trips.";
        }
        
        String userid = String.valueOf(user.get(0).getUserId());

        if (trip.getPassengerId().equalsIgnoreCase(userid)) {
            return "User has already selected this trip.";
        }
        else if (trip.getPassengerId() == null || trip.getPassengerId() == "") {
            trip.setPassengerId(userid);
        } else {
            trip.appendPassengerId(userid);
        }
        user.get(0).incrementTripNumber();
        em.merge(user);
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
            if (user.get(0).getUserId() == trip.getDriverId()) {
                user.get(0).setTripNumber(user.get(0).getTripNumber() - 1);
                ArrayList<String> ids = Helper.tokenizer(trip.getPassengerId(), ";");
                for (String s : ids) {
                    User passenger = userRep.getUser(Integer.parseInt(s));
                    passenger.setTripNumber(passenger.getTripNumber() - 1);
                    em.merge(passenger);
                }

                em.merge(user.get(0));
                em.remove(trip);
                return "Trip " + aTripID + "deleted";
            }
        }
        // Is user is passenger, just remove passenger ID
        else if ("Passenger".equalsIgnoreCase(user.get(0).getRole())) {
            List<String> ids = Helper.tokenizer(trip.getPassengerId(), ";");
            List<String> newIds = new ArrayList<String>(ids);
            for (String s : ids) {
                if (s.equals(String.valueOf(user.get(0).getUserId()))) {
                    newIds.remove(s);
                    user.get(0).setTripNumber(user.get(0).getTripNumber() - 1);
                    em.merge(user);
                }
            }
            ids = newIds;
            trip.setPassengerId(Helper.concatenator(ids, ";"));
            em.merge(trip);
            return "Passenger " + username + " removed from trip " + aTripID + ".";
        }

        return "Unknown error.";
    }

    @Transactional
    public String changeTripStatus(int aTripID, String username, String password, int status) {
        ATrip trip = getTrip(aTripID);
        List<User> user = userRep.findUser(username);

        if (user.size() != 1 || trip == null) {
            return "User or trip does not exist.";
        }
        else if (!(user.get(0).getPassword().equals(password))) {
            return "Unable to authenticate user to change trip status.";
        }
        else if (!("Driver".equalsIgnoreCase(user.get(0).getRole()))) {
            return "Only a driver can change the status of a trip.";
        }
        else if (trip.getDriverId() != user.get(0).getUserId()) {
            return "Drivers can only change status of their own trip.";
        }

        trip.setStatus(status);
        em.merge(trip);

        return "Trip status changed successfully!";
    }

    @Transactional
    public List<String> findPassengersOnTrip(int tripid) {
        ATrip trip = getTrip(tripid);

        if (trip == null) {
            return new ArrayList<String>();
        } else {
            return Helper.tokenizer(trip.getPassengerId(), ";");
        }
    }

    @Transactional
    public int findDriverOnTrip(int tripid) {
        ATrip trip = getTrip(tripid);

        if (trip == null) {
            return -1;
        } else {
            return trip.getDriverId();
        }
    }

    @Transactional
    public List<Integer> userTrips(String username, String password) {
        List<User> user = userRep.findUser(username);
        List<ATrip> list = em.createQuery("SELECT * FROM ATrip").getResultList();
        int userId = user.get(0).getUserId();

        if (userRep.authorizeUser(username, password, "Driver") != -1) {
            // Display trips that driver is in charge of

            List<ATrip> filteredList = list.stream()
                .filter(u -> (u.getDriverId() == userId))
                .collect(Collectors.toList());
            List<Integer> result = new ArrayList<Integer>();

            for (ATrip trip : filteredList) {
                result.add(trip.getTripId());
            }

            return result;
        }
        else if (userRep.authorizeUser(username, password, "Passenger") != -1) {
            // Display trips that passenger is a part of

            List<Integer> result = new ArrayList<Integer>();

            // We'll have to search through each trip
            for (ATrip el : list) {
                List<String> idList = Helper.tokenizer(el.getPassengerId(), ";");

                for (String id : idList) {
                    if (id.equalsIgnoreCase(String.valueOf(userId))) {
                        result.add(el.getTripId());
                    }
                }
            }

            return result;
        } else {
            return new ArrayList<Integer>();
        }
    }

    @Transactional
    public List<Integer> findTrip(
        String startLocation, 
        String stops, 
        int startDate, 
        int endDate, 
        String vehicleType, 
        Double maxCost
    ) {
        List<ATrip> trips = em.createQuery("SELECT * FROM ATrip").getResultList();

        if (startLocation != "") {
            // only get trips with this startLocation
            trips = trips.stream()
                .filter(u -> u.getStartLocation().equalsIgnoreCase(startLocation))
                .collect(Collectors.toList());
        }

        if (stops != "") {
            // only get trips with these stops
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                if (trip.getStops().contains(stops)) {
                    newList.add(trip);
                }
            }
            trips = newList;
        }

        if (startDate != -1) {
            // only get trips the leave at this day
            // (with a 1 hour margin of error)
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                if (trip.getStartDate() - 3600 <= startDate && startDate <= trip.getStartDate() + 3600) {
                    newList.add(trip);
                }
            }
            trips = newList;
        }

        if (endDate != -1) {
            // only get trips the arrive at this day
            // (with a 2 hour margin of error, since arrival can be unpredictable)
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                if (trip.getEndDate() - 7200 <= endDate && endDate <= trip.getEndDate() + 7200) {
                    newList.add(trip);
                }
            }
            trips = newList;
        }

        if (vehicleType != "") {
            // only get trips with a vehicle of this type
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                Vehicle vehicle = vehicleRep.getVehicle(trip.getVehicleId());
                if (vehicle != null) {
                    if (vehicle.getVehicleType().equalsIgnoreCase(vehicleType)) {
                        newList.add(trip);
                    }
                }
            }
            trips = newList;
        }

        if (maxCost != -1.0) {
            // only get trips that the user can pay for
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                List<String> costs = Helper.tokenizer(trip.getCostPerStop(), ";");
                double totalCost = 0;
                // We did not consider just the cost up to the user's destination
                // We should do that
                for (String cost : costs) {
                    totalCost += Double.parseDouble(cost);
                }
                if (totalCost <= maxCost) {
                    newList.add(trip);
                }
            }
            trips = newList;
        }

        // We only need the IDs of the filtered trips
        List<Integer> tripId = new ArrayList<Integer>();
        for (ATrip trip : trips) {
            tripId.add(trip.getTripId());
        }
        return tripId;
    }
}
