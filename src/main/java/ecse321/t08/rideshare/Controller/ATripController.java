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

	  @RequestMapping(value="/createtrip", method=RequestMethod.POST)
	  @ResponseBody
	  public String createTrip(@RequestParam(value="status", required=true) int status,
							   @RequestParam(value="cost", required=true) String cost,
							   @RequestParam(value="startDate", required=true) int startDate,
							   @RequestParam(value="endDate", required=true) int endDate,
							   @RequestParam(value="startLocation", required=true) String startLocation,
							   @RequestParam(value="stops", required=true) String stops,
							   @RequestParam(value="vehicleId", required=true) int vehicleId) {
		  ATrip newTrip = repository.createATrip(status, cost, startDate, endDate, startLocation, stops, vehicleId);
		  return "Trip created starting at " + startLocation + "!";
	  }

	  @RequestMapping(value="/trips/{id}", method=RequestMethod.GET)
	  	  public ATrip getTrip(@PathVariable("id") int id) {
		  ATrip trip = repository.getTrip(id);
		  return trip;
	  }


}
