package t08.ecse321.driverrideshare;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


    @RunWith(MockitoJUnitRunner.class)
    public class TestRegisterVehicle {
        @Mock
        RegisterVehicle activity = new RegisterVehicle();

        final String DRIVERUSERNAME = "testuser";
        final String DRIVERUSER_EXISTS = "exists";
        final String PASSWORD = "password";
        final String VEHICLETYPE = "Sedan";
        final String VEHICLEINPUTBAD = "";
        final String VEHICLEMODEL = "Ford Focus";
        final String VEHICLECOLOUR = "Blue";
        final int VEHICLENBOFSEATS = 4;
        final int VEHICLENBOFSEATSBAD = 1;
        final String VEHICLENBOFSEATSSTR = "4";

        @Before
        public void setMockOutput() {
            when(activity.registerVehiclePost(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                    .thenAnswer((InvocationOnMock invocation) -> {
                        if (invocation.getArgument(0).equals(DRIVERUSERNAME) && invocation.getArgument(1).equals(PASSWORD)){
                            return true;
                        } else if (invocation.getArgument(0).equals(DRIVERUSER_EXISTS)&& invocation.getArgument(1).equals(PASSWORD)){
                            return false;
                        }
                        return false;
                    });
        }

        @Test
        public void testRegisterSuccess(){
            boolean success = activity.registerVehiclePost(DRIVERUSERNAME, PASSWORD, VEHICLENBOFSEATSSTR, VEHICLECOLOUR,VEHICLEMODEL, VEHICLETYPE);
            assertTrue(success);
        }

        public void testRegisterVehicleNotAllFieldsFilled(){
            testRegisterVehicleTypeEmpty();
            testRegisterVehicleModelEmpty();
            testRegisterVehicleColourEmpty();
            testRegisterVehicleNbOfSeatsInvalid();
        }

        @Test
        public void testRegisterVehicleTypeEmpty() {
            boolean success = activity.checkRegisterVehicleType(VEHICLEINPUTBAD);
            assertFalse(success);
        }

        @Test
        public void testRegisterVehicleModelEmpty() {
            boolean success = activity.checkRegisterVehicleModel(VEHICLEINPUTBAD);
            assertFalse(success);
        }

        @Test
        public void testRegisterVehicleColourEmpty() {
            boolean success = activity.checkRegisterVehicleColour(VEHICLEINPUTBAD);
            assertFalse(success);
        }

        @Test
        public void testRegisterVehicleNbOfSeatsInvalid() {
            boolean success = activity.checkRegisterVehicleNbOfSeats(VEHICLENBOFSEATSBAD);
            assertFalse(success);
        }

    }


