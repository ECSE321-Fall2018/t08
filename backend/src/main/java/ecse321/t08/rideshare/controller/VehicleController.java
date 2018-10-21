package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.Vehicle;
import ecse321.t08.rideshare.repository.VehicleRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vehicle")
public class VehicleController {
    @Autowired
    VehicleRepository repository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createVehicle(
        @RequestParam("driveruser") String username,
        @RequestParam("driverpass") String password,
        @RequestParam("nbOfSeats") Integer nbOfSeats,
        @RequestParam("colour") String colour,
        @RequestParam("model") String model,
        @RequestParam("vehicleType") String vehicleType
    ) {
        Vehicle result = repository.createVehicle(username, password, nbOfSeats, colour, model, vehicleType);
        if (result != null) {
            JSONObject json = new JSONObject();
            json.put("data",  model + " created!");
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            JSONObject json = new JSONObject();
            json.put("data","Vehicle could not be created. Please verify credentials.");
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getVehicle(@PathVariable("id") int id) {
        Vehicle vehicle = repository.getVehicle(id);
        if(vehicle == null) {
            return new ResponseEntity<>(vehicle, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/finddriver", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> findVehicleForDriver(@RequestParam("driverusername") String username, @RequestParam("driverpassword") String password) {
        int result = repository.findVehicleForDriver(username, password);
        if(result == -1) {
            JSONObject json = new JSONObject();
            json.put("data", result);
            return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);
        } else {
            JSONObject json = new JSONObject();
            json.put("data", result);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
    }
}
