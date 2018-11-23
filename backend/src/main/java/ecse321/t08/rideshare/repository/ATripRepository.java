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
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ATripRepository {

    @PersistenceContext
    EntityManager em;

    @Autowired
    UserRepository userRep;

    @Autowired
    VehicleRepository vehRep;

    private final String PASSENGER_ROLE = "Passenger";
    private final String DRIVER_ROLE = "Driver";
    private final String ADMINISTRATOR_ROLE = "Administrator";

    @Transactional
    public ATrip createATrip(
        int status,
        String cost,
        long startDate,
        long endDate,
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
        User user = userRep.getUserUnsecured(driverId);
        if (!(user.getRole().equalsIgnoreCase(DRIVER_ROLE))) {
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

    @Transactional
    public ATrip modifyTrip(
            int tripid,
            String cost,
            long startdate,
            long enddate,
            String startloc,
            String stops,
            String username,
            String password
    ) {

        int driverid = userRep.authorizeUser(username, password, DRIVER_ROLE);
        if (driverid == -1) {
            return null;
        }
        ATrip trip = em.find(ATrip.class, tripid);

        if(trip == null) {
            return null;
        }

        if(trip.getDriverid() != driverid) {
            return null;
        }

        if(!(cost.equals(""))) {
            if (!(trip.getCostPerStop().equalsIgnoreCase(cost))) {
                trip.setCostPerStop(cost);
            }
        }
        if(!(startdate == -1)) {
            if (!(trip.getStartdate() == startdate)) {
                trip.setStartdate(startdate);
            }
        }
        if(!(enddate == -1)) {
            if (!(trip.getEnddate() == enddate)) {
                trip.setEnddate(enddate);
            }
        }
        if(!(startloc.equals(""))) {
            if (!(trip.getStartLocation().equalsIgnoreCase(startloc))) {
                trip.setStartLocation(startloc);
            }
        }
        if(!(stops.equals(""))) {
            if (!(trip.getStops().equalsIgnoreCase(stops))) {
                trip.setStops(stops);
            }
        }

        em.merge(trip);
        return trip;
    }


    // If you are an admin, you get to see all the trips
    @Transactional
    public List getUnfilteredTripsList(String username, String password) {
        if (userRep.authorizeUser(username, password, ADMINISTRATOR_ROLE) == -1 ) {
            return new ArrayList<User>();
        }
        return em.createNamedQuery("ATrip.findAll").getResultList();
    }

    @Transactional
    public ATrip getTrip(int id) {
        return em.find(ATrip.class, id);
    }

    // User selects trip and we record it on ATrip
    @Transactional
    public String selectTrip(int aTripID, String username, String password) {
        ATrip trip = getTrip(aTripID);

        if(trip == null) {
            return "Trip does not exist.";
        }

        int userId = userRep.authorizeUser(username, password, PASSENGER_ROLE);
        if(userId == -1) {
            return "User password incorrect, user must be a passenger.";
        }
        User user = userRep.getUserUnsecured(userId);

        if(trip.getPassengerid() != null && !(trip.getPassengerid().equals(""))) {
            List<String> passengerIds = rideshareHelper.tokenizer(trip.getPassengerid(), ";");
            for (String id : passengerIds) {
                if (id.equalsIgnoreCase(String.valueOf(user.getUserID()))) {
                    return "Passenger already selected this trip.";
                }
            }
        }
        if (trip.getPassengerid() == null || trip.getPassengerid().equals("")) {
            trip.setPassengerid(String.valueOf(user.getUserID()));
        } else {
            trip.setPassengerid(trip.getPassengerid() + ";" + String.valueOf(user.getUserID()));
        }
        user.setTripnumber(user.getTripnumber() + 1);
        em.merge(user);
        em.merge(trip);
        return ("");
    }

    // Cancel a trip if you are a user
    @Transactional
    public String cancelATrip(int aTripID, String username, String password) {
        ATrip trip = getTrip(aTripID);
        if(trip == null) {
            return "Trip does not exist.";
        }

        int userId = userRep.authenticateUser(username, password);
        if(userId == -1) {
            return "User password incorrect.";
        }
        User user = userRep.getUserUnsecured(userId);

        // If user is driver, delete entire trip
        if (DRIVER_ROLE.equalsIgnoreCase(user.getRole())) {
            if (user.getUserID() == trip.getDriverid()) {
                user.setTripnumber(user.getTripnumber() - 1);
                if(trip.getPassengerid() != null && !(trip.getPassengerid().equals(""))) {
                    ArrayList<String> ids = rideshareHelper.tokenizer(trip.getPassengerid(), ";");
                    for (String s : ids) {
                        if(!s.equals("")) {
                            User passenger = userRep.getUserUnsecured(Integer.parseInt(s));
                            if (passenger != null) {
                                passenger.setTripnumber(passenger.getTripnumber() - 1);
                                em.merge(passenger);
                            }
                        }
                    }
                }
                em.merge(user);
                em.remove(trip);
                return "";
            }
        }

        // Is user is passenger, just remove passenger ID
        if (PASSENGER_ROLE.equalsIgnoreCase(user.getRole())) {
            if(trip.getPassengerid() != null && !(trip.getPassengerid().equals(""))) {
                List<String> ids = rideshareHelper.tokenizer(trip.getPassengerid(), ";");
                List<String> newIds = new ArrayList<String>(ids);
                for (String s : ids) {
                    if (s.equals(String.valueOf(user.getUserID()))) {
                        newIds.remove(s);
                        user.setTripnumber(user.getTripnumber() - 1);
                        em.merge(user);
                    }
                }
                ids = newIds;
                trip.setPassengerid(rideshareHelper.concatenator(ids, ";"));
                em.merge(trip);
                return "";
            }
        }
        return "Unknown error.";
    }

    @Transactional
    public String changeTripStatus(int aTripID, String username, String password, int status) {
        ATrip trip = getTrip(aTripID);

        if(trip == null) {
            return "Trip does not exist.";
        }

        int userId = userRep.authorizeUser(username, password, DRIVER_ROLE);
        if(userId == -1) {
            return "User password incorrect, user must be a driver.";
        }
        User user = userRep.getUserUnsecured(userId);

        if (trip.getDriverid() != user.getUserID()) {
            return "Driver is not assigned to this trip.";
        }

        trip.setStatus(status);
        em.merge(trip);
        return "";
    }

    @Transactional
    public List<String> findPassengerOnTrip(int tripid) {
        ATrip trip = getTrip(tripid);

        if (trip == null || trip.getPassengerid() == null || trip.getPassengerid().equals("")) {
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
        int userId = userRep.authenticateUser(username, password);
        if(userId == -1) {
            return new ArrayList<Integer>();
        }
        User user = userRep.getUserUnsecured(userId);

        List<ATrip> trips = em.createNamedQuery("ATrip.findAll").getResultList();

        if (user.getRole().equalsIgnoreCase(DRIVER_ROLE)) {
            List<ATrip> filteredlist = trips.stream().filter(u -> (u.getDriverid() == user.getUserID()))
                    .collect(Collectors.toList());
            List<Integer> result = new ArrayList<Integer>();
            for (ATrip trip : filteredlist) {
                result.add(trip.getTripid());
            }
            return result;
        }

        if (user.getRole().equalsIgnoreCase(PASSENGER_ROLE)) {
            List<Integer> result = new ArrayList<Integer>();
            for (ATrip trip : trips) {
                if(trip.getPassengerid()!= null && !(trip.getPassengerid().equals(""))) {
                    List<String> idlist = rideshareHelper.tokenizer(trip.getPassengerid(), ";");
                    for (String id : idlist) {
                        if (id.equalsIgnoreCase(String.valueOf(user.getUserID()))) {
                            result.add(trip.getTripid());
                        }
                    }
                }
            }
            return result;
        }
        return new ArrayList<Integer>();
    }

    @Transactional
    public List<userTripStatus> usertripstatus(String username, String password, int status, String role) {
        if(userRep.authorizeUser(username, password, ADMINISTRATOR_ROLE) == -1) {
            return new ArrayList<>();
        }

        List<ATrip> trips = em.createNamedQuery("ATrip.findAll").getResultList();
        if(trips == null || trips.isEmpty()) {
            return new ArrayList<>();
        }

        return findUserOnTripWithStatus(status, trips, role);
    }

    //Inner class to store and return result of userTripStatus and findUserOnTripWithStatus to generate JSON
    public class userTripStatus {
        public userTripStatus(int userid, String username, String role, String startlocation, String stops, int tripid) {
            this.userid = userid;
            this.username = username;
            this.userrole = role;
            this.startlocation = startlocation;
            this.stops = stops;
            this.tripid = tripid;
        }

        public int userid;
        public String username;
        public String userrole;
        public String startlocation;
        public String stops;
        public int tripid;
    }

    public List<userTripStatus> findUserOnTripWithStatus(int status, List<ATrip> trips, String role) {

        List<Integer> userIds = new ArrayList<Integer>();
        List<userTripStatus> result = new ArrayList<userTripStatus>();

        for(ATrip trip: trips) { //Iterates through all trips
            if(trip.getStatus() == status) {
                if(role.equalsIgnoreCase(PASSENGER_ROLE)) {
                    if(trip.getPassengerid() != null && !trip.getPassengerid().equals("")) {
                        List<String> idlist = rideshareHelper.tokenizer(trip.getPassengerid(), ";"); //Gets list of passenger on trip
                        for (String idPass : idlist) { //Iterates through all passengers on trip
                            if (!idPass.equals("")) {
                                boolean onTrip = false;
                                for (Integer idRecorded : userIds) { //Iterates through all users already recorded, if passenger is already on list, will not add again
                                    if (idPass.equals(String.valueOf(idRecorded))) {
                                        onTrip = true;
                                    }
                                }

                                if (!onTrip) {
                                    userIds.add(Integer.parseInt(idPass));
                                    User user = userRep.getUserUnsecured(Integer.parseInt(idPass));
                                    if (user != null) {
                                        result.add(new userTripStatus(user.getUserID(), user.getUsername(), user.getRole(), trip.getStartLocation(), trip.getStops(), trip.getTripid()));
                                    }
                                }
                            }
                        }
                    }
                } else if (role.equalsIgnoreCase(DRIVER_ROLE)) {

                    //Checks to make sure that driver no already on trip
                    boolean onTrip = false;
                    for (Integer idRecorded : userIds) { //Iterates through all users already recorded, if driver is already on list, will not add again
                        if (trip.getDriverid() == idRecorded) {
                            onTrip = true;
                        }
                    }

                    if (!onTrip) {
                        userIds.add(trip.getDriverid());
                        User user = userRep.getUserUnsecured(trip.getDriverid());
                        if(user != null) {
                            result.add(new userTripStatus(user.getUserID(), user.getUsername(), user.getRole(), trip.getStartLocation(), trip.getStops(), trip.getTripid()));
                        }
                    }
                }
            }
        }
        return result;
    }

    @Transactional
    public List<ATrip> findtripstatus(String username, String password, int status) {
        if(userRep.authorizeUser(username, password, ADMINISTRATOR_ROLE) == -1) {
            return new ArrayList<>();
        }

        List<ATrip> trips = em.createNamedQuery("ATrip.findAll").getResultList();
        if(trips == null || trips.isEmpty()) {
            return new ArrayList<>();
        }

        return findTripWithStatus(status, trips);
    }

    public List<ATrip> findTripWithStatus(int status, List<ATrip> trips) {
        return trips.stream().filter(trip -> trip.getStatus() == status)
                .collect(Collectors.toList());
    }

    //Inner class to store and return result of ranking and getUserRankings to generate JSON
    public class userTripRanking {
        public userTripRanking(int userid, String username, String role, long tripnumber) {
            this.userid = userid;
            this.username = username;
            this.userrole = role;
            this.tripnumber = tripnumber;
        }

        public int userid;
        public String username;
        public String userrole;
        public long tripnumber;
    }

    @Transactional
    public List<userTripRanking> ranking(String username, String password, long start, long end, String role) {
        if(userRep.authorizeUser(username, password, ADMINISTRATOR_ROLE) == -1) {
            return new ArrayList<>();
        }

        List<ATrip> trips = em.createNamedQuery("ATrip.findAll").getResultList();
        if(trips == null || trips.isEmpty()) {
            return new ArrayList<>();
        }

        return getUserRankings(start, end, trips, role);
    }

    public List<userTripRanking> getUserRankings(long start, long end, List<ATrip> trips, String role) {

        //Gets all trips between start and end date with status completed (2)
        trips = trips.stream().filter(trip -> trip.getStartdate() >= start && trip.getEnddate() <= end && trip.getStatus() == 2)
                .collect(Collectors.toList());

        //Adds userids to list
        List<Integer> userIds = new ArrayList<Integer>();
        for(ATrip trip: trips) {
            if (role.equalsIgnoreCase(PASSENGER_ROLE)) {
                if(trip.getPassengerid() != null && !trip.getPassengerid().equals("")) {
                    List<String> idlist = rideshareHelper.tokenizer(trip.getPassengerid(), ";"); //Gets list of passenger on trip
                    for (String idPass : idlist) { //Iterates through all passengers on trip
                        if (!idPass.equals("")) {
                            userIds.add(Integer.parseInt(idPass));
                        }
                    }
                }
            } else if (role.equalsIgnoreCase(DRIVER_ROLE)) {
                userIds.add(trip.getDriverid());
            }
        }

        //Counts number of user ids and groups by userid, count number
        Map<Integer, Long> countedUsers = userIds.stream()
                .collect(Collectors.groupingBy(e->e, Collectors.counting()));

        List<userTripRanking> result = new ArrayList<userTripRanking>();

        //Iterates over each map item, formats it and adds it to return list
        for(Map.Entry<Integer, Long> entry: countedUsers.entrySet()) {
            User user = userRep.getUserUnsecured(entry.getKey());
            if(user != null) {
                result.add(new userTripRanking(user.getUserID(), user.getUsername(), user.getRole(), entry.getValue()));
            }
        }

        return result;
    }

    @Transactional
    public List<String> popularroute(String username, String password, long start, long end) {
        if(userRep.authorizeUser(username, password, ADMINISTRATOR_ROLE) == -1) {
            return new ArrayList<>();
        }

        List<ATrip> trips = em.createNamedQuery("ATrip.findAll").getResultList();
        if(trips == null || trips.isEmpty()) {
            return new ArrayList<>();
        }

        return getPopularRoutes(start, end, trips);
    }

    public List<String> getPopularRoutes(long start, long end, List<ATrip> trips) {

        //Gets all trips between start and end date
        trips = trips.stream().filter(trip -> trip.getStartdate() >= start && trip.getEnddate() <= end)
                .collect(Collectors.toList());

        //Adds routes to list
        List<String> routes = new ArrayList<String>();
        for(ATrip trip: trips) {
            List<String> stopList = rideshareHelper.tokenizer(trip.getStops(), ";"); //Gets list of stops on trip
            for (String stop : stopList) { //Iterates through all stops of trip to generate routes
                if(!stop.equals("")) {
                    routes.add(trip.getStartLocation()+ " - " + stop);
                }
            }
        }

        //Counts number of routes and groups by route, count number
        Map<String, Long> countedTrips = routes.stream()
                .collect(Collectors.groupingBy(e->e, Collectors.counting()));

        List<String> result = new ArrayList<String>();

        //Iterates over each map item, formats it and adds it to return list
        for(Map.Entry<String, Long> entry: countedTrips.entrySet()) {
            result.add(entry.getKey() + ";" + String.valueOf(entry.getValue()));
        }
        return result;
    }

    @Transactional
    public List<Integer> findtrip(String startLocation, String stop, long startdate, long enddate, String vehtype, Double maxcost) {
        List<ATrip> trips = em.createNamedQuery("ATrip.findAll").getResultList();
        if(trips != null && !(trips.isEmpty())) {
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                if (trip.getStatus() == 1) {
                    newList.add(trip);
                }
            }
            trips.clear();
            trips = newList.stream().collect(Collectors.toList());
        } else {
            return new ArrayList<Integer>();
        }

        if (!(startLocation.equals(""))) {
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                if (trip.getStartLocation().toUpperCase().contains(startLocation.toUpperCase())) {
                        newList.add(trip);
                }
            }
            trips.clear();
            trips = newList.stream().collect(Collectors.toList());
        }
        if (!(stop.equals(""))) {
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                List<String> stops = rideshareHelper.tokenizer(trip.getStops(), ";");
                for (String end : stops) {
                    if (end.toUpperCase().contains(stop.toUpperCase())) {
                        newList.add(trip);
                        break; //If found one trip, moves to next trip so that does not add same trip multiple times
                    }
                }
            }
            trips.clear();
            trips = newList.stream().collect(Collectors.toList());
        }

        //Finds start and end date within hours (unix time stamp)
        if (startdate != -1) {
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                if (trip.getStartdate() - 43200 <= startdate && startdate <= trip.getStartdate() + 43200) {
                    newList.add(trip);
                }
            }
            trips.clear();
            trips = newList.stream().collect(Collectors.toList());
        }

        if (enddate != -1) {
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                if (trip.getEnddate() - 43200 <= enddate && enddate <= trip.getEnddate() + 43200) {
                    newList.add(trip);
                }
            }
            trips.clear();
            trips = newList.stream().collect(Collectors.toList());
        }

        List<ATrip> newVList = new ArrayList<ATrip>();
        for (ATrip trip : trips) {
            Vehicle veh = vehRep.getVehicle(trip.getVehicleid());
            if (veh != null) {
                if(trip.getPassengerid() != null && !(trip.getPassengerid().equals(""))) {
                    List<String> idlist = rideshareHelper.tokenizer(trip.getPassengerid(), ";"); //get passengers on trip
                    if (idlist.size() >= veh.getNbOfSeats()) { //Checks to make sure that there are enough seats
                        continue; //If not enough seats, breaks and goes to next trip
                    }
                }
                if (!(vehtype.equals(""))) {
                    if (veh.getVehicleType().toUpperCase().contains(vehtype.toUpperCase())) {
                        newVList.add(trip);
                    }
                } else {
                    newVList.add(trip); //Case where no vehicle type specified, adds trip automatically if enough space
                }
            } else {
                newVList.add(trip); //Case where no vehicle exists, theoretically should not happen if DB in sync
            }
        }
        trips.clear();
        trips = newVList.stream().collect(Collectors.toList());


        if (maxcost != -1.0) {
            List<ATrip> newList = new ArrayList<ATrip>();
            for (ATrip trip : trips) {
                List<String> costs = rideshareHelper.tokenizer(trip.getCostPerStop(), ";");
                for (String cost : costs) {
                    if (Double.parseDouble(cost) <= maxcost) {
                        newList.add(trip);
                        break; //If found one trip, moves to next trip so that does not add same trip multiple times
                    }
                }
            }
            trips.clear();
            trips = newList.stream().collect(Collectors.toList());
        }
        List<Integer> tripid = new ArrayList<Integer>();
        for (ATrip trip : trips) {
            tripid.add(trip.getTripid());
        }
        return tripid.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}