package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.Vehicle;
import ecse321.t08.rideshare.repository.VehicleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.anyInt;
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
        assertEquals(DRIVER_ID, vehicleController.getVehicle(VEHICLE_ID).getDriverId());
    }


    @Test
    public void testVehicleQueryNotFound() {
        System.out.println("Testing Vehicle Query Not Found");
        assertNull(vehicleController.getVehicle(NONEXISTING_VEHICLE_ID));
    }
}
