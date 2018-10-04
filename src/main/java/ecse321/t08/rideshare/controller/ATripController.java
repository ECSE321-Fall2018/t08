package ecse321.t08.rideshare.controller;


import ecse321.t08.rideshare.entity.ATrip;
import ecse321.t08.rideshare.repository.ATripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/trip")
public class ATripController {

	  @Autowired
	  ATripRepository repository;

	  @RequestMapping(value="/createtrip", method=RequestMethod.POST)
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

	  @RequestMapping(value="/trips/{username}/{password}", method=RequestMethod.GET)
	  public List getTrips(
			@PathVariable("username") String username, 
			@PathVariable("password") String password
		) {
	  		return repository.getTrips(username, password);
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
