package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.ATrip;
import ecse321.t08.rideshare.repository.ATripRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ATripControllerTest {

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
    private static final int NON_EXISTING_TRIP_ID = -3;

    private ATrip aTrip;

    @Before
    public void setUp() {
        initMocks(this);

        aTrip = new ATrip();
        when(repository.getTrip(eq(TRIP_ID))).thenReturn(aTrip);
        when(repository.getTrip(eq(NON_EXISTING_TRIP_ID))).thenReturn(null);
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
        when(repository.createATrip(anyInt(), anyString(), anyInt(), anyInt(), eq(START_LOCATION), anyString(), anyInt(), anyInt()))
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
                DRIVER_ID);

        // Then
        verify(repository).createATrip(anyInt(), anyString(), anyInt(), anyInt(), eq(START_LOCATION), anyString(), anyInt(), anyInt());
        assertEquals(answer, result);
    }

    @Test
    public void getTrip() {
        // Given

        // When
        ATrip result = aTripController.getTrip(TRIP_ID);

        // Then
        verify(repository).getTrip(anyInt());
        assertEquals(result, aTrip);
    }

    @Test
    public void getTripUnsuccessful() {
        // Given

        // When
        ATrip result = aTripController.getTrip(NON_EXISTING_TRIP_ID);

        // Then
        verify(repository).getTrip(anyInt());
        assertNull(result);
    }
}