package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.Vehicle;
import ecse321.t08.rideshare.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vehicle")
public class VehicleController {

	@Autowired
    VehicleRepository repository;

    @RequestMapping(value="/createvehicle", method=RequestMethod.POST)
    @ResponseBody
    public String createVehicle(@RequestParam("driverId") int driverId,
                                @RequestParam("nbOfSeats") int nbOfSeats,
                                @RequestParam("colour") String colour,
                                @RequestParam("model") String model,
                                @RequestParam("vehicleType") String vehicleType) {

        repository.createVehicle(driverId, nbOfSeats, colour, model, vehicleType);
        return model + " created!";
    }

    @RequestMapping(value="/vehicles/{id}", method=RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) {
        return repository.getVehicle(id);
    }
}
