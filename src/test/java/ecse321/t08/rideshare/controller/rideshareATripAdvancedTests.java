package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.ATrip;
import ecse321.t08.rideshare.entity.User;
import ecse321.t08.rideshare.repository.ATripRepository;
import ecse321.t08.rideshare.utility.rideshareHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class rideshareATripAdvancedTests {

    @Mock
    ATripRepository repository;

    @InjectMocks
    ATripController aTripController;

    private static final int TRIP_ID = -1;
    private static final int TRIP_STATUS = 1;
    private static final String COST_PER_STOP = "5.00;8.00";
    private static final int START_DATE = 934238908;
    private static final int END_DATE = 934238918;
    private static final String START_LOCATION = "Montreal";
    private static final String STOPS = "Ottawa;Toronto";
    private static final int VEHICLE_ID = -2;
    private static final String PASSENGER_ID = "1;2;3;4";
    private static final int DRIVER_ID = 4;
    private static final String DRIVER_USERNAME = "drivertest";
    private static final String DRIVER_PASSWORD = "driverpass";
    private static final String ADMIN_USERNAME = "admintest";
    private static final String ADMIN_PASSWORD = "adminpass";
    private static final String PASSENGER_USERNAME = "passengertest";
    private static final String PASSENGER_PASSWORD = "passengerpass";
    private static final int NON_EXISTING_TRIP_ID = -3;

    private ATrip aTrip;

    @Before
    public void setUp() {
        initMocks(this);

        aTrip = new ATrip();
        when(repository.getTrip(eq(TRIP_ID))).thenReturn(aTrip);
        when(repository.getTrip(eq(NON_EXISTING_TRIP_ID))).thenReturn(null);
        when(repository.getUnfilteredTripsList(anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ADMIN_USERNAME) && invocation.getArgument(1).equals(ADMIN_PASSWORD)) {
                ATrip trip = new ATrip();
                List<ATrip> tripsList = new ArrayList<ATrip>();
                trip.setStartLocation(START_LOCATION);
                tripsList.add(trip);
                return tripsList;
            } else {
                return new ArrayList<ATrip>();
            }
        });
        when(repository.selectTrip(anyInt(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TRIP_ID) && invocation.getArgument(1).equals(PASSENGER_USERNAME) && invocation.getArgument(2).equals(PASSENGER_PASSWORD)) {
                return "Passenger " + PASSENGER_USERNAME + " selected this trip.";
            } else {
                return "User or trip does not exist.";
            }
        });
        when(repository.cancelATrip(anyInt(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TRIP_ID) && invocation.getArgument(1).equals(PASSENGER_USERNAME) && invocation.getArgument(2).equals(PASSENGER_PASSWORD)) {
                return "Passenger " + PASSENGER_USERNAME + " removed from trip " + TRIP_ID + ".";
            }
            else if (invocation.getArgument(0).equals(TRIP_ID) && invocation.getArgument(1).equals(DRIVER_USERNAME) && invocation.getArgument(2).equals(DRIVER_PASSWORD)) {
                return "Trip " + TRIP_ID + "deleted";
            } else {
                return "Unknown error.";
            }
        });
        when(repository.changeTripStatus(anyInt(), anyString(), anyString(), anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TRIP_ID) && invocation.getArgument(1).equals(DRIVER_USERNAME) && invocation.getArgument(2).equals(DRIVER_PASSWORD)) {
                return "Trip status changed successfully";
            } else {
                return "Only a driver can change the status of a trip.";
            }
        });
        when(repository.findPassengerOnTrip(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TRIP_ID)) {
                List<String> list = (rideshareHelper.tokenizer(PASSENGER_ID, ";"));
                return list;
            } else {
                return new ArrayList<User>();
            }
        });
        when(repository.findDriverOnTrip(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TRIP_ID)) {
                return DRIVER_ID;
            } else {
                return -1;
            }
        });
    }

    @Test
    public void createTrip() {
        // Given
        ATrip myTrip = new ATrip(
                TRIP_ID,
                TRIP_STATUS,
                COST_PER_STOP,
                START_DATE,
                END_DATE,
                START_LOCATION,
                STOPS,
                VEHICLE_ID,
                PASSENGER_ID,
                DRIVER_ID);
        when(repository.createATrip(anyInt(), anyString(), anyInt(), anyInt(), eq(START_LOCATION), anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(myTrip);
        String answer = "Trip created starting at " + START_LOCATION + "!";

        // When
        String result = aTripController.createTrip(
                TRIP_ID,
                COST_PER_STOP,
                START_DATE,
                END_DATE,
                START_LOCATION,
                STOPS,
                VEHICLE_ID,
                DRIVER_USERNAME,
                DRIVER_PASSWORD);

        // Then
        verify(repository).createATrip(anyInt(), anyString(), anyInt(), anyInt(), eq(START_LOCATION), anyString(), anyInt(), anyString(), anyString());
        assertEquals(answer, result);
    }

    @Test
    public void getTrip() {
        ATrip result = aTripController.getTrip(TRIP_ID);
        verify(repository).getTrip(anyInt());
        assertEquals(result, aTrip);
    }

    @Test
    public void getTripUnsuccessful() {
        ATrip result = aTripController.getTrip(NON_EXISTING_TRIP_ID);
        verify(repository).getTrip(anyInt());
        assertNull(result);
    }

    @Test
    public void getUnfilteredTripsList() {
        List<ATrip> result = aTripController.getUnfilteredTripsList(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertEquals(result.get(0).getStartLocation(), START_LOCATION);
    }

    @Test
    public void getUnfilteredTripsListUnsuccessful() {
        List<ATrip> result = aTripController.getUnfilteredTripsList(ADMIN_USERNAME, DRIVER_PASSWORD);
        assertTrue(result.isEmpty());
    }

    @Test
    public void selectTrip() {
        String result = aTripController.selectTrip(TRIP_ID, PASSENGER_USERNAME, PASSENGER_PASSWORD);
        assertEquals("Passenger " + PASSENGER_USERNAME + " selected this trip.", result);
    }

    @Test
    public void selectTripUnsuccessful() {
        String result = aTripController.selectTrip(TRIP_ID, DRIVER_USERNAME, PASSENGER_PASSWORD);
        assertEquals("User or trip does not exist.", result);
    }

    @Test
    public void cancelTripDriver() {
        String result = aTripController.cancelATrip(TRIP_ID, DRIVER_USERNAME, DRIVER_PASSWORD);
        assertEquals("Trip " + TRIP_ID + "deleted", result);
    }

    @Test
    public void cancelTripPassenger() {
        String result = aTripController.cancelATrip(TRIP_ID, PASSENGER_USERNAME, PASSENGER_PASSWORD);
        assertEquals("Passenger " + PASSENGER_USERNAME + " removed from trip " + TRIP_ID + ".", result);
    }

    @Test
    public void cancelTripUnsuccessful() {
        String result = aTripController.cancelATrip(TRIP_ID, ADMIN_USERNAME, ADMIN_PASSWORD);
        assertEquals("Unknown error.", result);
    }

    @Test
    public void changeTripStatus() {
        String result = aTripController.changeTripStatus(TRIP_ID, DRIVER_USERNAME, DRIVER_PASSWORD, TRIP_STATUS);
        assertEquals("Trip status changed successfully", result);
    }

    @Test
    public void changeTripStatusUnsuccessful() {
        String result = aTripController.changeTripStatus(TRIP_ID, PASSENGER_USERNAME, PASSENGER_PASSWORD, TRIP_STATUS);
        assertEquals("Only a driver can change the status of a trip.", result);
    }

    @Test
    public void passengerOnTrip() {
        List<String> result = aTripController.passengerOnTrip(TRIP_ID);
        int iter = 1;
        for(String s : result) {
            assertEquals(String.valueOf(iter), result.get(iter-1));
            iter++;
        }
    }

    @Test
    public void passengerOnTripUnsuccessful() {
        List<String> result = aTripController.passengerOnTrip(NON_EXISTING_TRIP_ID);
        assertTrue(result.isEmpty());
    }

    @Test
    public void driverOnTrip() {
        int result = aTripController.driverOnTrip(TRIP_ID);
        assertEquals(DRIVER_ID, result);
    }

    @Test
    public void driverOnTripUnsuccessful() {
       int result = aTripController.driverOnTrip(NON_EXISTING_TRIP_ID);
        assertEquals(-1, result);
    }

}