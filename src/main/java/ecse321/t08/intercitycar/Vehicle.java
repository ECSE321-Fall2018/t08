/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4201.5b72717f7 modeling language!*/


import java.util.*;

// line 65 "model.ump"
// line 144 "model.ump"
public class Vehicle
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Vehicle Attributes
  private String manufacturer;
  private String model;
  private String colour;
  private int nbOfSeats;

  //Vehicle Associations
  private Driver driver;
  private List<ATrip> aTrips;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Vehicle(String aManufacturer, String aModel, String aColour, int aNbOfSeats, Driver aDriver)
  {
    manufacturer = aManufacturer;
    model = aModel;
    colour = aColour;
    nbOfSeats = aNbOfSeats;
    if (aDriver == null || aDriver.getVehicle() != null)
    {
      throw new RuntimeException("Unable to create Vehicle due to aDriver");
    }
    driver = aDriver;
    aTrips = new ArrayList<ATrip>();
  }

  public Vehicle(String aManufacturer, String aModel, String aColour, int aNbOfSeats, enum aRatingForDriver)
  {
    manufacturer = aManufacturer;
    model = aModel;
    colour = aColour;
    nbOfSeats = aNbOfSeats;
    driver = new Driver(aRatingForDriver, this);
    aTrips = new ArrayList<ATrip>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setManufacturer(String aManufacturer)
  {
    boolean wasSet = false;
    manufacturer = aManufacturer;
    wasSet = true;
    return wasSet;
  }

  public boolean setModel(String aModel)
  {
    boolean wasSet = false;
    model = aModel;
    wasSet = true;
    return wasSet;
  }

  public boolean setColour(String aColour)
  {
    boolean wasSet = false;
    colour = aColour;
    wasSet = true;
    return wasSet;
  }

  public boolean setNbOfSeats(int aNbOfSeats)
  {
    boolean wasSet = false;
    nbOfSeats = aNbOfSeats;
    wasSet = true;
    return wasSet;
  }

  public String getManufacturer()
  {
    return manufacturer;
  }

  public String getModel()
  {
    return model;
  }

  public String getColour()
  {
    return colour;
  }

  public int getNbOfSeats()
  {
    return nbOfSeats;
  }
  /* Code from template association_GetOne */
  public Driver getDriver()
  {
    return driver;
  }
  /* Code from template association_GetMany */
  public ATrip getATrip(int index)
  {
    ATrip aATrip = aTrips.get(index);
    return aATrip;
  }

  public List<ATrip> getATrips()
  {
    List<ATrip> newATrips = Collections.unmodifiableList(aTrips);
    return newATrips;
  }

  public int numberOfATrips()
  {
    int number = aTrips.size();
    return number;
  }

  public boolean hasATrips()
  {
    boolean has = aTrips.size() > 0;
    return has;
  }

  public int indexOfATrip(ATrip aATrip)
  {
    int index = aTrips.indexOf(aATrip);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfATrips()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ATrip addATrip(int aPickupTime, double aCost, String aDate, int aTripID, enum aStatus, String aStartLocation, String aEndLocation, String aInfo, Trips aTrips, Driver aDriver)
  {
    return new ATrip(aPickupTime, aCost, aDate, aTripID, aStatus, aStartLocation, aEndLocation, aInfo, this, aTrips, aDriver);
  }

  public boolean addATrip(ATrip aATrip)
  {
    boolean wasAdded = false;
    if (aTrips.contains(aATrip)) { return false; }
    Vehicle existingVehicle = aATrip.getVehicle();
    boolean isNewVehicle = existingVehicle != null && !this.equals(existingVehicle);
    if (isNewVehicle)
    {
      aATrip.setVehicle(this);
    }
    else
    {
      aTrips.add(aATrip);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeATrip(ATrip aATrip)
  {
    boolean wasRemoved = false;
    //Unable to remove aATrip, as it must always have a vehicle
    if (!this.equals(aATrip.getVehicle()))
    {
      aTrips.remove(aATrip);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addATripAt(ATrip aATrip, int index)
  {  
    boolean wasAdded = false;
    if(addATrip(aATrip))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfATrips()) { index = numberOfATrips() - 1; }
      aTrips.remove(aATrip);
      aTrips.add(index, aATrip);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveATripAt(ATrip aATrip, int index)
  {
    boolean wasAdded = false;
    if(aTrips.contains(aATrip))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfATrips()) { index = numberOfATrips() - 1; }
      aTrips.remove(aATrip);
      aTrips.add(index, aATrip);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addATripAt(aATrip, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Driver existingDriver = driver;
    driver = null;
    if (existingDriver != null)
    {
      existingDriver.delete();
    }
    for(int i=aTrips.size(); i > 0; i--)
    {
      ATrip aATrip = aTrips.get(i - 1);
      aATrip.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "manufacturer" + ":" + getManufacturer()+ "," +
            "model" + ":" + getModel()+ "," +
            "colour" + ":" + getColour()+ "," +
            "nbOfSeats" + ":" + getNbOfSeats()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "driver = "+(getDriver()!=null?Integer.toHexString(System.identityHashCode(getDriver())):"null");
  }
}