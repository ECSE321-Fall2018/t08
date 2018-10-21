package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.ATrip;
import ecse321.t08.rideshare.repository.ATripRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/trip")
public class ATripController {
    @Autowired
    ATripRepository repository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createTrip(
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
            JSONObject json = new JSONObject();
            json.put("data", "Unable to create trip.");
            return new ResponseEntity<>(json.toString(), HttpStatus.FORBIDDEN);
        } else {
            JSONObject json = new JSONObject();
            json.put("data", "Trip created starting at" + startLocation +"!");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
    }

    // Get list of trips if you are admin
    @RequestMapping(value = "/utripslist", method = RequestMethod.POST)
    public ResponseEntity<?> getUnfilteredTripsList(
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        List<ATrip> list = repository.getUnfilteredTripsList(username, password);
        if(list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/trips/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTrip(@PathVariable("id") int id) {
        ATrip trip = repository.getTrip(id);
        if(trip == null) {
            return new ResponseEntity<>(trip, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(trip, HttpStatus.OK);
        }
    }

    // User selects trip and we record it on ATrip
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public ResponseEntity<?> selectTrip(
        @RequestParam("tripid") int ATripID,
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        String result = repository.selectTrip(ATripID, username, password);
        if(result == "") {
            JSONObject json = new JSONObject();
            json.put("data", result);
            return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
        } else {
            JSONObject json = new JSONObject();
            json.put("data", result);
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);

        }
    }

    // Cancel trip based on ID, if you are a user
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseEntity<?> cancelATrip(
        @RequestParam("tripid") int ATripID,
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        String result = repository.cancelATrip(ATripID, username, password);
        if(result == "") {
            JSONObject json = new JSONObject();
            json.put("data", result);
            return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
        } else {
            JSONObject json = new JSONObject();
            json.put("data", result);
            return new ResponseEntity<>(json , HttpStatus.OK);

        }
    }


    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public ResponseEntity<?> changeTripStatus(
        @RequestParam("tripid") Integer ATripID,
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("tripstatus") Integer status
    ) {
        String result = repository.changeTripStatus(ATripID, username, password, status); // 0 for ongoing, 1 for planned, 2 for completed
        if(result == "") {
            JSONObject json = new JSONObject();
            json.put("data", result);
            return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
        } else {
            JSONObject json = new JSONObject();
            json.put("data", result);
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
    }

    //Finds passenger on trip tripid
    @RequestMapping(value = "/passengers", method = RequestMethod.POST)
    public ResponseEntity<?> passengerOnTrip(@RequestParam("tripid") Integer ATripID) {
        List<String> list = repository.findPassengerOnTrip(ATripID);
        if(list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        } else {
            JSONArray array = new JSONArray(list);
            return new ResponseEntity<>(array, HttpStatus.OK);
        }
    }

    //Finds driver on trip tripid
    @RequestMapping(value = "/driver", method = RequestMethod.POST)
    public ResponseEntity<?> driverOnTrip(@RequestParam("tripid") Integer ATripID) {
        int driverid =  repository.findDriverOnTrip(ATripID);
        if(driverid == -1) {
            JSONObject json = new JSONObject();
            json.put("data", driverid);
            return new ResponseEntity<>(json.toString(), HttpStatus.NOT_FOUND);
        } else {
            JSONObject json = new JSONObject();
            json.put("data", driverid);
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
    }

    //Finds all trips associated with user
    @RequestMapping(value = "/usertrips", method = RequestMethod.POST)
    public ResponseEntity<?> usertrip(@RequestParam("username") String username,
                                  @RequestParam("password") String password) {
        List<Integer> list = repository.userTrip(username, password);
        if(list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        } else {
            JSONArray array = new JSONArray(list);
            return new ResponseEntity<>(array, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> findTrip(
        @RequestParam(value = "startloc", required = false) String startLocation,
        @RequestParam(value = "stop", required = false) String stop,
        @RequestParam(value = "startdate", required = false, defaultValue = "-1") Integer startdate,
        @RequestParam(value = "enddate", required = false, defaultValue = "-1") Integer enddate,
        @RequestParam(value = "vehtype", required = false) String vehtype,
        @RequestParam(value = "maxcost", required = false, defaultValue = "-1.0") Double maxcost
    ) {
        if (startLocation == null) {
            startLocation = "";
        }
        if (stop == null) {
            stop = "";
        }
        if (startdate == null) {
            startdate = -1;
        }
        if (enddate == null) {
            enddate = -1;
        }
        if (vehtype == null) {
            vehtype = "";
        }
        if (maxcost == null) {
            maxcost = -1.0;
        }
        List<Integer> list = repository.findtrip(startLocation, stop, startdate, enddate, vehtype, maxcost);
        if(list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        } else {
            JSONArray array = new JSONArray(list);
            return new ResponseEntity<>(array, HttpStatus.OK);
        }
    }
}
