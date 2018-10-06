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

	@RequestMapping(value="/createtrip", method = RequestMethod.POST)
	@ResponseBody
    public String createTrip(
        @RequestParam("status") int status,
		@RequestParam("cost") String cost,
		@RequestParam("startDate") int startDate,
		@RequestParam("endDate") int endDate,
		@RequestParam("startLocation") String startLocation,
		@RequestParam("stops") String stops,
        @RequestParam("vehicleid") int vehicleId,
		@RequestParam("driverid") int driverId
    ) {
	    repository.createATrip(status, cost, startDate, endDate, startLocation, stops, vehicleId, driverId);
	    return "Trip created starting at " + startLocation + "!";
    }
        // Get list of trips if you are admin
	    @RequestMapping(value="/utripslist", method = RequestMethod.GET)
	    public List getUnfilteredTripsList(
		    @RequestParam("username") String username,
            @RequestParam("password") String password
        ) {
	  	    return repository.getUnfilteredTripsList(username, password);
	    }
        
	    @RequestMapping(value="/trips/{id}", method=RequestMethod.GET)
	    public ATrip getTrip(@PathVariable("id") int id) {
	        return repository.getTrip(id);
		}
		
		// User selects trip and we record it on ATrip
		@RequestMapping(value="/selecttrip", method = RequestMethod.GET)
		public String selectTrip(
			@RequestParam("tripid") int ATripID,
			@RequestParam("username") String username,
			@RequestParam("password") String password
		) {
			return repository.selectTrip(ATripID, username, password);
		}

        // Cancel trip based on ID, if you are a user
	    @RequestMapping(value="/cancelTrip", method = RequestMethod.DELETE)
	    public String cancelATrip(
            @RequestParam("tripid") int ATripID,
			@RequestParam("username") String username,
              @RequestParam("password") String password
        ) {
	  	    return repository.cancelATrip(ATripID, username, password);
	    }
}
