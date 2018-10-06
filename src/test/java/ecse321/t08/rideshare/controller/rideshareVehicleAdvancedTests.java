package ecse321.t08.rideshare.controller;


import ecse321.t08.rideshare.repository.VehicleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Transactional
public class rideshareVehicleAdvancedTests  {
    private static final String findall = "User.findAll";
    private static final String findUser = "User.findUsername";

    private static final int VEHICLE_ID = -3;
    private static final int DRIVER_ID = -2;
    private static final int NONEXISTING_DRIVER_ID = -4;

    @Mock
    EntityManager entityManager;

    @Mock
    Query query;

    @Mock
    VehicleRepository vehicleDao;

    @InjectMocks
    VehicleController vehicleController;

    // it looks like you are testing user controller so move the userDao part under ecse321.t08.rideshare.repository

    @Before
    public void setMockOutput() {
        when(vehicleDao.findVehicleForDriver(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(DRIVER_ID)) {
                return VEHICLE_ID;
            } else {
                return -1;
            }
        });
    }

    @Test
    public void testFindVehicleForDriver() {
        int result = vehicleController.findVehicleForDriver(DRIVER_ID);

        assertEquals(result, VEHICLE_ID);
    }

    @Test
    public void testFindVehicleForDriverFails() {
        int result = vehicleController.findVehicleForDriver(NONEXISTING_DRIVER_ID);

        assertEquals(-1, result);
    }

}