package ecse321.t08.rideshare.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ecse321.t08.rideshare.Entity.ATrip;
import ecse321.t08.rideshare.Repository.ATripRepository;

@RestController
@RequestMapping("api/aTrip")
public class ATripController {

	 @Autowired
	 ATripRepository repository;



	 @RequestMapping(value="/createATrip", method=RequestMethod.POST)
	 @ResponseBody
	 public String createATrip(int tripID, double cost, int date, String startLocation, String endLocation){
	 	ATrip newATrip = repository.createATrip(tripID, cost, date, startLocation, endLocation);
	 	return "ATrip " + tripID + " created!";
	 }
}
