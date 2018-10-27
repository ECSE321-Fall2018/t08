package t08.ecse321.driverrideshare;

import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.*;


public class TestCreateTrip {


    CreateTrip activity = new CreateTrip();

    private String COST1 = "1.2";
    private String COST2 = "1.3";
    private String COST3 = "1.4";
    private String BADCOST = "$1.4";
    private String STOP1 = "Ottawa";
    private String STOP2 = "Toronto";
    private String STOP3 = "Montreal";
    private String DATE = "01-02-2017";
    private String DATE_INVALID = "01/02/2017";
    private String TIME = "02:30";
    private String TIME_INVALID = "02-30:30";
    private long TIME_STAMP_MILLI = 1483324200000L;

    @Test
    public void testCheckCost() {
        List<Double> list = activity.checkCost(COST1, COST2, COST3);
        assertEquals(3, list.size());
    }

    @Test
    public void testCheckCostOneEmpty() {
        List<Double> list2 = activity.checkCost(COST1, COST2, COST3);
        assertEquals(3, list2.size());
    }

    @Test
    public void testCheckCostFailure1() {
        List<Double> list = activity.checkCost(COST1, BADCOST, COST3);
        assertTrue(list.isEmpty());
    }

    @Test
    public void testCheckCostFailure2() {
        List<Double> list = activity.checkCost("", "", "");
        assertTrue(list.isEmpty());
    }

    @Test
    public void testCheckStop1() {
        List<String> list = activity.checkStop(STOP1, STOP2, STOP3);
        assertEquals(3, list.size());

    }

    @Test
    public void testCheckStopOneNull() {
        List<String> list = activity.checkStop(STOP1, STOP2, null);
        assertEquals(2, list.size());

    }

    @Test
    public void testCheckStopFailure() {
        List<String> list = activity.checkStop("", "", null);
        assertTrue(list.isEmpty());
    }

    @Test
    public void testGetUnixTimeStamp() {
        long stamp = activity.getUnixStamp(DATE, TIME);
        assertEquals(TIME_STAMP_MILLI, stamp);
    }

    @Test
    public void testGetUnixTimeStampFailure1() {
        long stamp = activity.getUnixStamp(DATE_INVALID, TIME);
        assertEquals(-1, stamp);
    }

    @Test
    public void testGetUnixTimeStampFailure2() {
        long stamp = activity.getUnixStamp(DATE, TIME_INVALID);
        assertEquals(-1, stamp);
    }

    @Test
    public void testCheckTimeStamp() {
        boolean result = activity.checkTimeStamp(1, 2);
        assertTrue(result);
    }

    @Test
    public void testCheckTimeStampFailure() {
        boolean result = activity.checkTimeStamp(2, 1);
        assertFalse(result);
    }
}
