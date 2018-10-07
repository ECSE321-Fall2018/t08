package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.ATrip;
import ecse321.t08.rideshare.entity.User;
import ecse321.t08.rideshare.entity.Vehicle;
import ecse321.t08.rideshare.repository.ATripRepository;
import ecse321.t08.rideshare.utility.rideshareHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.*;
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
    private static final int TRIP_ID2 = -5;
    private static final int TRIP_ID3 = -10;
    private static final int TRIP_STATUS = 1;
    private static final Double MIN_COST = 2.00;
    private static final Double MAX_COST = 5.00;
    private static final String COST_PER_STOP = "5.00;8.00";
    private static final int START_DATE = 934238908;
    private static final int END_DATE = 934238918;
    private static final String START_LOCATION = "Montreal";
    private static final String STOPS = "Ottawa;Toronto";
    private static final String TEST_STOP = "Ottawa";
    private static final String TEST_FAKE_STOP = "Vancouver";
    private static final int VEHICLE_ID = -2;
    private static final String PASSENGER_ID = "1;2;3;4";
    private static final String NONEXISTING_PASSENGER_ID = "5;6;7;8";
    private static final int DRIVER_ID = 1;
    private static final int NONEXSITING_DRIVER_ID = 5;
    private static final String DRIVER_USERNAME = "drivertest";
    private static final String DRIVER_PASSWORD = "driverpass";
    private static final String ADMIN_USERNAME = "admintest";
    private static final String ADMIN_PASSWORD = "adminpass";
    private static final String PASSENGER_USERNAME = "passengertest";
    private static final String PASSENGER_PASSWORD = "passengerpass";
    private static final String VEH_TYPE = "VEH_TYPE";
    private static final int NON_EXISTING_TRIP_ID = -3;

    private ATrip aTrip;

    @Before
    public void setUp() {
        initMocks(this);

        aTrip = new ATrip();
        when(repository.getTrip(eq(TRIP_ID))).thenReturn(aTrip);
        when(repository.getTrip(eq(NON_EXISTING_TRIP_ID))).thenReturn(null);
        when(repository.getUnfilteredTripsList(anyString(), anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
            if (
                invocation.getArgument(0).equals(ADMIN_USERNAME)
                && invocation.getArgument(1).equals(ADMIN_PASSWORD)
            ) {
                ATrip trip = new ATrip();
                List<ATrip> tripsList = new ArrayList<ATrip>();
                trip.setStartLocation(START_LOCATION);
                tripsList.add(trip);
                return tripsList;
            } else {
                return new ArrayList<ATrip>();
            }
        });
        when(repository.selectTrip(anyInt(), anyString(), anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
            if (
                invocation.getArgument(0).equals(TRIP_ID)
                && invocation.getArgument(1).equals(PASSENGER_USERNAME)
                && invocation.getArgument(2).equals(PASSENGER_PASSWORD)
            ) {
                return "Passenger " + PASSENGER_USERNAME + " selected this trip.";
            } else {
                return "User or trip does not exist.";
            }
        });
        when(repository.cancelATrip(anyInt(), anyString(), anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
            if (
                invocation.getArgument(0).equals(TRIP_ID)
                && invocation.getArgument(1).equals(PASSENGER_USERNAME)
                && invocation.getArgument(2).equals(PASSENGER_PASSWORD)
            ) {
                return "Passenger " + PASSENGER_USERNAME + " removed from trip " + TRIP_ID + ".";
            } else if (invocation.getArgument(0).equals(TRIP_ID) && invocation.getArgument(1).equals(DRIVER_USERNAME) && invocation.getArgument(2).equals(DRIVER_PASSWORD)) {
                return "Trip " + TRIP_ID + "deleted";
            } else {
                return "Unknown error.";
            }
        });
        when(repository.changeTripStatus(anyInt(), anyString(), anyString(), anyInt()))
        .thenAnswer((InvocationOnMock invocation) -> {
            if (
                invocation.getArgument(0).equals(TRIP_ID)
                && invocation.getArgument(1).equals(DRIVER_USERNAME)
                && invocation.getArgument(2).equals(DRIVER_PASSWORD)
            ) {
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
        when(repository.userTrip(anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            User user = new User();
            if (
                invocation.getArgument(0).equals(PASSENGER_USERNAME)
                && invocation.getArgument(1).equals(PASSENGER_PASSWORD)
            ) {
                user.setRole("Passenger");
            } else if (
                invocation.getArgument(0).equals(DRIVER_USERNAME)
                && invocation.getArgument(1).equals(DRIVER_PASSWORD)
            ) {
                user.setRole("Driver");
            } else {
                return new ArrayList<Integer>();
            }

            ATrip trip = new ATrip();
            ATrip trip2 = new ATrip();
            ATrip trip3 = new ATrip();

            user.setUsername(PASSENGER_ID);
            user.setPassword(PASSENGER_PASSWORD);
            user.setUserID(1);
            trip.setPassengerid(PASSENGER_ID);
            trip.setTripid(TRIP_ID);
            trip.setDriverid(DRIVER_ID);
            trip2.setTripid(TRIP_ID2);
            trip2.setPassengerid(PASSENGER_ID);
            trip2.setDriverid(DRIVER_ID);
            trip3.setTripid(TRIP_ID3);
            trip3.setPassengerid(NONEXISTING_PASSENGER_ID);
            trip3.setDriverid(NONEXSITING_DRIVER_ID);
            List<ATrip> tripsList = new ArrayList<ATrip>();
            tripsList.add(trip);
            tripsList.add(trip2);
            tripsList.add(trip3);
            if (user.getRole().equalsIgnoreCase("Driver")) {
                List<ATrip> flist = tripsList.stream().filter(u -> (u.getDriverid() == 1))
                    .collect(Collectors.toList());
                List<Integer> result = new ArrayList<Integer>();
                for (ATrip i : flist) {
                    result.add(i.getTripid());
                }
                return result;
            }
            if (user.getRole().equalsIgnoreCase("Passenger")) {
                List<Integer> result = new ArrayList<Integer>();
                for (ATrip el : tripsList) {
                    List<String> idlist = rideshareHelper.tokenizer(el.getPassengerid(), ";");
                    for (String id : idlist) {
                        if (id.equalsIgnoreCase(String.valueOf(user.getUserID()))) {
                            result.add(el.getTripid());
                        }
                    }
                }
                return result;
            }
            return new ArrayList<Integer>();
        });
        when(repository.findtrip(
            anyString(), anyString(), anyInt(), anyInt(), anyString(), anyDouble(), anyDouble()
        ))
        .thenAnswer((InvocationOnMock invocation) -> {
            ATrip trip1 = new ATrip();
            Vehicle veh = new Vehicle();
            veh.setVehicleId(1);
            veh.setVehicleType(VEH_TYPE);

            List<ATrip> trips = new ArrayList<ATrip>();
            trip1.setStartLocation(START_LOCATION);
            trip1.setTripid(TRIP_ID);
            trip1.setStops(STOPS);
            trip1.setStartDate(START_DATE);
            trip1.setEndDate(END_DATE);
            trip1.setCostPerStop(COST_PER_STOP);
            trip1.setVehicleid(1);
            trips.add(trip1);

            if (invocation.getArgument(1).equals(TEST_STOP)) {
                List<ATrip> newList = new ArrayList<ATrip>(trips);

                for (ATrip trip : trips) {
                    List<String> stops = rideshareHelper.tokenizer(trip.getStops(), ";");
                    boolean found = false;
                    for (String end : stops) {
                        if (end.toUpperCase().contains(TEST_STOP.toUpperCase())) {
                            found = true;
                        }
                    }
                    if (found == false) {
                        newList.remove(trip);
                    }
                }
                trips = newList;
                List<Integer> tripid = new ArrayList<Integer>();
                for (ATrip trip : trips) {
                    tripid.add(trip.getTripid());
                }
                return tripid;
            } else if (invocation.getArgument(1).equals(TEST_FAKE_STOP)) {
                List<ATrip> newList = new ArrayList<ATrip>(trips);
                for (ATrip trip : trips) {
                    List<String> stops = rideshareHelper.tokenizer(trip.getStops(), ";");
                    boolean found = false;
                    for (String end : stops) {
                        if (end.toUpperCase().contains(TEST_FAKE_STOP.toUpperCase())) {
                            found = true;
                        }
                    }
                    if (found == false) {
                        newList.remove(trip);
                    }
                }
                trips = newList;
                List<Integer> tripid = new ArrayList<Integer>();
                for (ATrip trip : trips) {
                    tripid.add(trip.getTripid());
                }
                return tripid;
            } else {
                return new ArrayList<Integer>();
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
            DRIVER_ID
        );

        when(repository.createATrip(
            anyInt(), 
            anyString(), 
            anyInt(), 
            anyInt(), 
            eq(START_LOCATION), 
            anyString(), 
            anyInt(), 
            anyString(), 
            anyString()
        )).thenReturn(myTrip);
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
            DRIVER_PASSWORD
        );

        // Then
        verify(repository).createATrip(
            anyInt(), 
            anyString(), 
            anyInt(), 
            anyInt(), 
            eq(START_LOCATION), 
            anyString(), 
            anyInt(), 
            anyString(), 
            anyString()
        );
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
        String result = aTripController.changeTripStatus(
            TRIP_ID, 
            DRIVER_USERNAME, 
            DRIVER_PASSWORD, 
            TRIP_STATUS
        );
        assertEquals("Trip status changed successfully", result);
    }

    @Test
    public void changeTripStatusUnsuccessful() {
        String result = aTripController.changeTripStatus(
            TRIP_ID, 
            PASSENGER_USERNAME, 
            PASSENGER_PASSWORD, 
            TRIP_STATUS
        );
        assertEquals("Only a driver can change the status of a trip.", result);
    }

    @Test
    public void passengerOnTrip() {
        List<String> result = aTripController.passengerOnTrip(TRIP_ID);
        int iter = 1;
        for (String s : result) {
            assertEquals(String.valueOf(iter), result.get(iter - 1));
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

    @Test
    public void userTripDriver() {
        List<Integer> result = aTripController.usertrip(DRIVER_USERNAME, DRIVER_PASSWORD);
        assertEquals((int) result.get(0), TRIP_ID);
        assertEquals((int) result.get(1), TRIP_ID2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void userTripDriverFailure() {
        List<Integer> result = aTripController.usertrip(DRIVER_USERNAME, DRIVER_PASSWORD);
        result.get(2);
    }

    @Test
    public void userTripPassenger() {
        List<Integer> result = aTripController.usertrip(PASSENGER_USERNAME, PASSENGER_PASSWORD);
        assertEquals((int) result.get(0), TRIP_ID);
        assertEquals((int) result.get(1), TRIP_ID2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void userTripPassengerFailure() {
        List<Integer> result = aTripController.usertrip(PASSENGER_USERNAME, PASSENGER_PASSWORD);
        result.get(2);
    }

    @Test
    public void userTripFailure() {
        List<Integer> result = aTripController.usertrip(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertTrue(result.isEmpty());
    }

    @Test
    public void findTrip() {
        List<Integer> result = aTripController.findTrip(
            START_LOCATION, 
            TEST_STOP, 
            START_DATE, 
            END_DATE, 
            VEH_TYPE, 
            MIN_COST, 
            MAX_COST
        );
        assertEquals((int) result.get(0), (int) TRIP_ID);
    }

    @Test
    public void findTripUnsuccessful() {
        List<Integer> result = aTripController.findTrip(
            START_LOCATION, 
            TEST_FAKE_STOP, 
            START_DATE, 
            END_DATE, 
            VEH_TYPE, 
            MIN_COST, 
            MAX_COST
        );
        assertTrue(result.isEmpty());
    }
}