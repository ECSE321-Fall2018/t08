package ecse321.t08.rideshare.controller;


import ecse321.t08.rideshare.repository.VehicleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.ws.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Transactional
public class rideshareVehicleAdvancedTests {
    private static final String findall = "User.findAll";
    private static final String findUser = "User.findUsername";

    private static final int VEHICLE_ID = -3;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String NONEXISTING_PASSWORD= "badpassword";

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
        when(vehicleDao.findVehicleForDriver(anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USERNAME) && invocation.getArgument(1).equals(PASSWORD)) {
                return VEHICLE_ID;
            } else {
                return -1;
            }
        });
    }

    @Test
    public void testFindVehicleForDriver() {
        ResponseEntity<?> result = vehicleController.findVehicleForDriver(USERNAME, PASSWORD);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testFindVehicleForDriverFails() {
        ResponseEntity<?> result = vehicleController.findVehicleForDriver(USERNAME, NONEXISTING_PASSWORD);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

}