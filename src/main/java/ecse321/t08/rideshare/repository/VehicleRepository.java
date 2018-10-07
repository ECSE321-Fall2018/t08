package ecse321.t08.rideshare.repository;

import ecse321.t08.rideshare.entity.User;
import ecse321.t08.rideshare.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class VehicleRepository {
    @PersistenceContext
    EntityManager em;

    @Autowired
    UserRepository userRepo;

    @Transactional
    public Vehicle createVehicle(
        String driverUsername, 
        String driverPassword, 
        int nbOfSeats, 
        String colour, 
        String model, 
        String vehicleType
    ) {
        int driverId = userRepo.authorizeUser(driverUsername, driverPassword, "Driver");
        if (driverId == -1) {
            return null;
        }

        Vehicle vehicle = new Vehicle(driverId, vehicleType, nbOfSeats, model, colour);
        em.persist(vehicle);
        return vehicle;
    }

    @Transactional
    public Vehicle getVehicle(int id) {
        return em.find(Vehicle.class, id);
    }

    @Transactional
    public int findVehicleForDriver(int driverId) {
        List<Vehicle> vehicleList = em.createNamedQuery("Vehicle.findAll").getResultList();
        vehicleList = vehicleList.stream()
            .filter(u -> u.getDriverId() == driverId)
            .collect(Collectors.toList());

        if (vehicleList.size() != 1) {
            return -1;
        } else {
            return vehicleList.get(0).getVehicleId();
        }
    }
}
