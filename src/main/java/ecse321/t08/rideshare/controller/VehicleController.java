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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public String createVehicle(
        @RequestParam("driveruser") String username,
        @RequestParam("driverpass") String password,
        @RequestParam("nbOfSeats") Integer nbOfSeats,
        @RequestParam("colour") String colour,
        @RequestParam("model") String model,
        @RequestParam("vehicleType") String vehicleType
    ) {
        Vehicle result = repository.createVehicle(username, password, nbOfSeats, colour, model, vehicleType);
        if (result != null) {
            return model + " created!";
        } else {
            return "Vehicle could not be created. Please verify credentials.";
        }
    }

    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) {
        return repository.getVehicle(id);
    }

    @RequestMapping(value = "/finddriver", method = RequestMethod.POST)
    @ResponseBody
    public int findVehicleForDriver(@RequestParam("driverid") Integer driverid) {
        return repository.findVehicleForDriver(driverid);
    }
}
