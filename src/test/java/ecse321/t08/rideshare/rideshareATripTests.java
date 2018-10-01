package ecse321.t08.rideshare;

import ecse321.t08.rideshare.Controller.ATripController;
import ecse321.t08.rideshare.Entity.ATrip;
import ecse321.t08.rideshare.Repository.ATripRepository;
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
public class rideshareATripTests {

    @Mock
    private ATripRepository tripDao;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ATripController tripController;

    private static final int TRIP_ID = -1;
    private static final int TRIP_STATUS = 1;
    private static final String COST_PER_STOP = "5.00;8.00";
    private static final int START_DATE = 934238908;
    private static final int END_DATE = 934238918;
    private static final String START_LOCATION = "Montreal";
    private static final String STOPS = "Ottawa;Toronto";
    private static final int VEHICLE_ID = -2;
    private static final String PASSENGER_ID = "1;2;3;4";
    private static final int NON_EXISTING_TRIP_ID = -3;


    @Before
    public void setMockTrueOutput() {
        System.out.println("Setting Up Test For Vehicle Query Found");
        when(tripDao.getTrip(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(TRIP_ID)) {
                ATrip trip = new ATrip();
                trip.setTripID(TRIP_ID);
                trip.setStatus(TRIP_STATUS);
                trip.setCostPerStop(COST_PER_STOP);
                trip.setStartDate(START_DATE);
                trip.setEndDate(END_DATE);
                trip.setStartLocation(START_LOCATION);
                trip.setStops(STOPS);
                trip.setVehicleId(VEHICLE_ID);
                trip.setPassengerId(PASSENGER_ID);
                return trip;
            } else {
                return null;
            }
        });
    }


    @Test
    public void testTripSimpleQueryFound() {
        System.out.println("Testing Trip Query Found");
        assertEquals(tripController.getTrip(TRIP_ID).getStops(), STOPS);
    }


    @Test
    public void testUserQueryNotFound() {
        System.out.println("Testing Trip Query Not Found");
        assertEquals(tripController.getTrip(NON_EXISTING_TRIP_ID), null);
    }
}
