package ecse321.t08.rideshare.Controller;

import ecse321.t08.rideshare.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ecse321.t08.rideshare.Entity.ATrip;
import ecse321.t08.rideshare.Repository.ATripRepository;
import java.util.*;

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

		@RequestMapping(value="/getTrip", method=RequestMethod.GET)
		public String getTrip(@RequestParam(value = "cost", required = false) Double cost,
                              @RequestParam(value = "date", required = false) Integer date,
                              @RequestParam(value = "startL", required = false) String startLocation,
                              @RequestParam(value = "endL", required = false) String endLocation) {
			ATrip findTrip = repository.getTrip(cost, date, startLocation, endLocation);

			if (findTrip == null) {
				return "TRIP NOT FOUND";
			}
			return "TRIP FOUND";
			//return findTrip.getTrip();
		}
		//value = "logout", required = false
//        @RequestMapping(value = "/name")
//        String getName(@RequestParam(value = "person", required = false) String personName){
//            return "Required element of request param";
       // }
	    //OK create new method like getUser from userController
		//OK take input of all attributes related to trip (trip id, cost, etc...)
		//OK RequestMapping/getTrip, RequestMethod.GET
		//OK Use similar method than userController
		//create appropriate method in repository
		//use appropriate strings uppercase methods to make searches

}
