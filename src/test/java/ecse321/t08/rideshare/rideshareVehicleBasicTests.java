package ecse321.t08.rideshare;

import ecse321.t08.rideshare.Controller.VehicleController;
import ecse321.t08.rideshare.Entity.Vehicle;
import ecse321.t08.rideshare.Repository.VehicleRepository;
import org.mockito.InjectMocks;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class rideshareVehicleBasicTests {

    @Mock
    private VehicleRepository vehicleDao;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private VehicleController vehicleController;

    private static final int DRIVER_ID = -3;
    private static final int SEATS = 4;
    private static final String COLOUR = "blue";
    private static final String MODEL = "CarModel";
    private static final String VEHICLE_TYPE = "Minivan";
    private static final int VEHICLE_ID = -1;
    private static final int NONEXISTING_VEHICLE_ID = -2;


    @Before
    public void setMockTrueOutput() {
        System.out.println("Setting Up Test For Vehicle Query Found");
        when(vehicleDao.getVehicle(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VEHICLE_ID)) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(VEHICLE_ID);
                vehicle.setNbOfSeats(SEATS);
                vehicle.setColour(COLOUR);
                vehicle.setDriverId(DRIVER_ID);
                vehicle.setModel(MODEL);
                vehicle.setVehicleType(VEHICLE_TYPE);
                return vehicle;
            } else {
                return null;
            }
        });
    }


    @Test
    public void testVehicleSimpleQueryFound() {
        System.out.println("Testing Vehicle Query Found");
        assertEquals(vehicleController.getVehicle(VEHICLE_ID).getDriverId(), DRIVER_ID);
    }


    @Test
    public void testVehicleQueryNotFound() {
        System.out.println("Testing Vehicle Query Not Found");
        assertEquals(vehicleController.getVehicle(NONEXISTING_VEHICLE_ID), null);
    }
}
