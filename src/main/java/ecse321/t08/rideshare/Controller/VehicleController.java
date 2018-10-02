package ecse321.t08.rideshare.Controller;

import ecse321.t08.rideshare.Entity.ATrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ecse321.t08.rideshare.Entity.Vehicle;
import ecse321.t08.rideshare.Repository.VehicleRepository;

@RestController
@RequestMapping("api/vehicle")
public class VehicleController {

	@Autowired
    VehicleRepository repository;


    @RequestMapping(value="/createvehicle", method=RequestMethod.POST)
    @ResponseBody
    public String createVehicle(@RequestParam(value="driverId", required=true) int driverId,
                                @RequestParam(value="nbOfSeats", required=true) int nbOfSeats,
                                @RequestParam(value="colour", required=true) String colour,
                                @RequestParam(value="model", required=true) String model,
                                @RequestParam(value="vehicleType", required=true) String vehicleType) {

            Vehicle vehicle = repository.createVehicle(driverId, nbOfSeats, colour, model, vehicleType);
        return model + " created!";
    }

    @RequestMapping(value="/vehicles/{id}", method=RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) {
        Vehicle vehicle = repository.getVehicle(id);
        return vehicle;
    }
}
