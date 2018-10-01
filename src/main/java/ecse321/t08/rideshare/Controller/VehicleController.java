package ecse321.t08.rideshare.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ecse321.t08.rideshare.Entity.Vehicle;
import ecse321.t08.rideshare.Repository.VehicleRepository;

@RestController
@RequestMapping("api/vehicle")
public class VehicleController {

	@Autowired
    VehicleRepository repository;
	
    @RequestMapping(value="/createVehicle", method=RequestMethod.POST)
    @ResponseBody
    public String createVehicle(int nbOfSeats, String colour, String model){
        Vehicle newVehicle = repository.createVehicle(nbOfSeats, colour, model);
        return colour + model + "vehicle created!";
    }
}
