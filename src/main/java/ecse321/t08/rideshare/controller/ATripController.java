package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.ATrip;
import ecse321.t08.rideshare.repository.ATripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/trip")
public class ATripController {
    @Autowired
    ATripRepository repository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public String createTrip(
        @RequestParam("status") Integer status,
        @RequestParam("cost") String cost,
        @RequestParam("startDate") Integer startDate,
        @RequestParam("endDate") Integer endDate,
        @RequestParam("startLocation") String startLocation,
        @RequestParam("stops") String stops,
        @RequestParam("vehicleid") Integer vehicleId,
        @RequestParam("driveruser") String driverUsername,
        @RequestParam("driverpass") String driverPassword
    ) {
        ATrip result = repository.createATrip(
            status, 
            cost, 
            startDate, 
            endDate, 
            startLocation, 
            stops, 
            vehicleId, 
            driverUsername, 
            driverPassword
        );
        if (result == null) {
            return "Unable to create trip.";
        } else {
            return "Trip created starting at " + startLocation + "!";
        }
    }

    // Get list of trips if you are admin
    @RequestMapping(value = "/utripslist", method = RequestMethod.POST)
    public List getUnfilteredTripsList(
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        return repository.getUnfilteredTripsList(username, password);
    }

    @RequestMapping(value = "/trips/{id}", method = RequestMethod.GET)
    public ATrip getTrip(@PathVariable("id") int id) {
        return repository.getTrip(id);
    }

    // User selects trip and we record it on ATrip
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String selectTrip(
        @RequestParam("tripid") int ATripID,
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        return repository.selectTrip(ATripID, username, password);
    }

    // Cancel trip based on ID, if you are a user
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancelATrip(
        @RequestParam("tripid") int ATripID,
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        return repository.cancelATrip(ATripID, username, password);
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public String changeTripStatus(
        @RequestParam("tripid") int ATripID,
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("tripstatus") int status
    ) {
        return repository.changeTripStatus(ATripID, username, password, status); // 0 for ongoing, 1 for planned, 2 for completed
    }

    @RequestMapping(value = "/passengersontrip", method = RequestMethod.POST)
    public List<String> passengersOnTrip(@RequestParam("tripid") int ATripID) {
        return repository.findPassengersOnTrip(ATripID);
    }

    @RequestMapping(value = "/driverontrip", method = RequestMethod.POST)
    public int driverOnTrip(@RequestParam("tripid") int ATripID) {
        return repository.findDriverOnTrip(ATripID);
    }

    @RequestMapping(value = "/usertrips", method = RequestMethod.POST)
    public List<Integer> userTrips(
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        return repository.userTrips(username, password);
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public List<Integer> findTrip(
        @RequestParam(value = "startLocation", required = false) String startLocation,
        @RequestParam(value = "stop", required = false) String stops,
        @RequestParam(value = "startDate", required = false, defaultValue = "-1") Integer startDate,
        @RequestParam(value = "endDate", required = false, defaultValue = "-1") Integer endDate,
        @RequestParam(value = "vehicleType", required = false) String vehicleType,
        @RequestParam(value = "maxcost", required = false, defaultValue = "-1.0") Double maxCost
    ) {
        if (startLocation == null) {
            startLocation = "";
        }
        if (stops == null) {
            stops = "";
        }
        if (startDate == null) {
            startDate = -1;
        }
        if (endDate == null) {
            endDate = -1;
        }
        if (vehicleType == null) {
            vehicleType = "";
        }
        if (maxCost == null) {
            maxCost = -1.0;
        }
        return repository.findTrip(startLocation, stops, startDate, endDate, vehicleType, maxCost);
    }
}
