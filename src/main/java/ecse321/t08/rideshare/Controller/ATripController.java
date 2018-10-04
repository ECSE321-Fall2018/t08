package ecse321.t08.rideshare.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ecse321.t08.rideshare.Entity.ATrip;
import ecse321.t08.rideshare.Repository.ATripRepository;
import java.util.*;

@RestController
@RequestMapping("api/trip")
public class ATripController {

	  @Autowired
	  ATripRepository repository;

	  @RequestMapping(value="/createTrip", method=RequestMethod.POST)
	  @ResponseBody
	  public String createTrip(@RequestParam("status") int status,
							   @RequestParam("cost") String cost,
							   @RequestParam("startDate") int startDate,
							   @RequestParam("endDate") int endDate,
							   @RequestParam("startLocation") String startLocation,
							   @RequestParam("stops") String stops,
							   @RequestParam("vehicleId") int vehicleId) {
		  repository.createATrip(status, cost, startDate, endDate, startLocation, stops, vehicleId);
		  return "Trip created starting at " + startLocation + "!";
	  }

	  @RequestMapping(value="/trips/{id}", method=RequestMethod.GET)
	  public ATrip getTrip(@PathVariable("id") int id) {
	  		return repository.getTrip(id);
	  }

	  @RequestMapping(value="/cancelTrip", method=RequestMethod.DELETE)
	  public String cancelATrip(@RequestParam("tripID") int ATripID, @RequestParam("userID") int userID) {
	  	repository.cancelATrip(ATripID, userID);
	  	return "Trip number: " + ATripID + "has been cancelled by user " + userID + "!";
	  }


}
