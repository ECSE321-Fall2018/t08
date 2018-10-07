package ecse321.t08.rideshare.repository;

import ecse321.t08.rideshare.entity.ATrip;
import ecse321.t08.rideshare.entity.User;
import ecse321.t08.rideshare.entity.Vehicle;
import ecse321.t08.rideshare.utility.rideshareHelper;
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
    VehicleRepository vehRep;

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
        if (driverId == -1) {
            return null;
        }
        User user = userRep.getUser(driverId);
        if (!(user.getRole().equalsIgnoreCase("Driver"))) {
            return null;
        }
        user.setTripnumber(user.getTripnumber() + 1);
        em.merge(user);

        aTrip.setStatus(status);
        aTrip.setCostPerStop(cost);
        aTrip.setStartdate(startDate);
        aTrip.setEnddate(endDate);
        aTrip.setStartLocation(startLocation);
        aTrip.setStops(stops);
        aTrip.setVehicleid(vehicleId);
        aTrip.setDriverid(driverId);
        em.persist(aTrip);
        return aTrip;
    }

    // If you are an admin, you get to see all the trips
    @Transactional
    public List getUnfilteredTripsList(String username, String password) {
        List<User> user = userRep.findUser(username);
        // Check if user is admin
        if (userRep.authorizeUser(username, password, "Administrator") == -1 ) {
            return new ArrayList<User>();
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

        if (trip.getPassengerid().equalsIgnoreCase(String.valueOf(user.get(0).getUserID()))) {
            return "User has already selected this trip.";
        }
        if (trip.getPassengerid() == null || trip.getPassengerid().equals("")) {
            trip.setPassengerid(String.valueOf(user.get(0).getUserID()));
        } else {
            trip.setPassengerid(trip.getPassengerid() + ";" + String.valueOf(user.get(0).getUserID()));
        }
        user.get(0).setTripnumber(user.get(0).getTripnumber() + 1);
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
        }
        if (!(user.get(0).getPassword().equals(password))) {
            return "Unable to authenticate user to cancel trip.";
        }
        // If user is driver, delete entire trip
        if ("Driver".equalsIgnoreCase(user.get(0).getRole())) {
            if (user.get(0).getUserID() == trip.getDriverid()) {
                user.get(0).setTripnumber(user.get(0).getTripnumber() - 1);
                ArrayList<String> ids = rideshareHelper.tokenizer(trip.getPassengerid(), ";");
                for (String s : ids) {
                    User passenger = userRep.getUser(Integer.parseInt(s));
                    passenger.setTripnumber(passenger.getTripnumber() - 1);
                    em.merge(passenger);
                }

                em.merge(user.get(0));
                em.remove(trip);
                return "Trip " + aTripID + "deleted";
            }
        }
        // Is user is passenger, just remove passenger ID
        if ("Passenger".equalsIgnoreCase(user.get(0).getRole())) {
            List<String> ids = rideshareHelper.tokenizer(trip.getPassengerid(), ";");
            List<String> newIds = new ArrayList<String>(ids);
            for (String s : ids) {
                if (s.equals(String.valueOf(user.get(0).getUserID()))) {
                    newIds.remove(s);
                    user.get(0).setTripnumber(user.get(0).getTripnumber() - 1);
                    em.merge(user);
                }
            }
            ids = newIds;
            trip.setPassengerid(rideshareHelper.concatenator(ids, ";"));
            em.merge(trip);
            return "Passenger " + username + " removed from trip " + aTripID + ".";
        }

        return "Unknown error.";
    }

    @Transactional
    public String changeTripStatus(int aTripID, String username, String password, int status) {
        ATrip trip = getTrip(aTripID);
        List<User> user = userRep.findUser(username);

        // Let's make sure user and trip exist, and user password is correct.

        if (user.size() != 1 || trip == null) {
            return "User or trip does not exist.";
        }
        if (!(user.get(0).getPassword().equals(password))) {
            return "Unable to authenticate user to change trip status.";
        }
        // Check if user is driver
        if (!("Driver".equalsIgnoreCase(user.get(0).getRole()))) {
            return "Only a driver can change the status of a trip.";
        }
        if (trip.getDriverid() != user.get(0).getUserID()) {
            return "Driver can only change status of their own trip.";
        }

        trip.setStatus(status);
        em.merge(trip);
        return "Trip status changed successfully";
    }

    @Transactional
    public List<String> findPassengerOnTrip(int tripid) {
        ATrip trip = getTrip(tripid);

        if (trip == null) {
            return new ArrayList<String>();
        }
        return rideshareHelper.tokenizer(trip.getPassengerid(), ";");
    }

    @Transactional
    public int findDriverOnTrip(int tripid) {
        ATrip trip = getTrip(tripid);

        if (trip == null) {
            return -1;
        }
        return trip.getDriverid();
    }

    @Transactional
    public List<Integer> userTrip(String username, String password) {
        List<User> user = userRep.findUser(username);

        if (userRep.authenticateUser(username, password) == -1) {
            return new ArrayList<Integer>();
        }


        List<ATrip> trips = em.createQuery("SELECT * FROM ATrip").getResultList();
        if (user.get(0).getRole().equalsIgnoreCase("Driver")) {
            List<ATrip> filteredlist = trips.stream().filter(u -> (u.getDriverid() == user.get(0).getUserID()))
                    .collect(Collectors.toList());
            List<Integer> result = new ArrayList<Integer>();
            for (ATrip trip : filteredlist) {
                result.add(trip.getTripid());
            }
            return result;
        }
        if (user.get(0).getRole().equalsIgnoreCase("Passenger")) {
            List<Integer> result = new ArrayList<Integer>();
            for (ATrip trip : trips) {
                List<String> idlist = rideshareHelper.tokenizer(trip.getPassengerid(), ";");
                for (String id : idlist) {
                    if (id.equalsIgnoreCase(String.valueOf(user.get(0).getUserID()))) {
                        result.add(trip.getTripid());
                    }
                }
            }
            return result;
        }
        return new ArrayList<Integer>();
    }

    @Transactional
    public List<Integer> findtrip(String startLocation, String stop, int startdate, int enddate, String vehtype, Double maxcost) {
        List<ATrip> trips = em.createQuery("SELECT * FROM ATrip").getResultList();
        if (!(startLocation.equals(""))) {
            trips = trips.stream()
                .filter(u -> u.getStartLocation().toUpperCase().contains(startLocation.toUpperCase()))
                .collect(Collectors.toList());
        }
        if (!(stop.equals(""))) {
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                List<String> stops = rideshareHelper.tokenizer(trip.getStops(), ";");
                for (String end : stops) {
                    if (end.toUpperCase().contains(stop.toUpperCase())) {
                        newList.add(trip);
                    }
                }
            }
            trips = newList;
        }

        //Finds start and end date within hours (unix time stamp)
        if (startdate != -1) {
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                if (trip.getStartdate() - 3600 <= startdate && startdate <= trip.getStartdate() + 3600) {
                    newList.add(trip);
                }
            }
            trips = newList;
        }

        if (enddate != -1) {
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                if (trip.getEnddate() - 7200 <= enddate && enddate <= trip.getEnddate() + 7200) {
                    newList.add(trip);
                }
            }
            trips = newList;
        }

        if (!(vehtype.equals(""))) {
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                Vehicle veh = vehRep.getVehicle(trip.getVehicleid());
                if (veh != null) {
                    if (veh.getVehicleType().toUpperCase().contains(vehtype.toUpperCase())) {
                        newList.add(trip);
                    }
                }
            }
            trips = newList;
        }

        if (maxcost != -1.0) {
            List<ATrip> newList = new ArrayList<ATrip>(trips);
            for (ATrip trip : trips) {
                List<String> costs = rideshareHelper.tokenizer(trip.getCostPerStop(), ";");
                for (String cost : costs) {
                    if (Double.parseDouble(cost) <= maxcost) {
                        newList.add(trip);
                    }
                }
            }
            trips = newList;
        }
        List<Integer> tripid = new ArrayList<Integer>();
        for (ATrip trip : trips) {
            tripid.add(trip.getTripid());
        }
        return tripid;
    }
}
