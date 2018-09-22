/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4201.5b72717f7 modeling language!*/


import java.util.*;

// line 21 "model.ump"
// line 111 "model.ump"
public class Driver extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Driver Attributes
  private enum rating;

  //Driver Associations
  private Vehicle vehicle;
  private List<ATrip> aTrips;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Driver(enum aRating, Vehicle aVehicle)
  {
    super();
    rating = aRating;
    if (aVehicle == null || aVehicle.getDriver() != null)
    {
      throw new RuntimeException("Unable to create Driver due to aVehicle");
    }
    vehicle = aVehicle;
    aTrips = new ArrayList<ATrip>();
  }

  public Driver(enum aRating, String aManufacturerForVehicle, String aModelForVehicle, String aColourForVehicle, int aNbOfSeatsForVehicle)
  {
    super();
    rating = aRating;
    vehicle = new Vehicle(aManufacturerForVehicle, aModelForVehicle, aColourForVehicle, aNbOfSeatsForVehicle, this);
    aTrips = new ArrayList<ATrip>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRating(enum aRating)
  {
    boolean wasSet = false;
    rating = aRating;
    wasSet = true;
    return wasSet;
  }

  public enum getRating()
  {
    return rating;
  }
  /* Code from template association_GetOne */
  public Vehicle getVehicle()
  {
    return vehicle;
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
  public ATrip addATrip(int aPickupTime, double aCost, String aDate, int aTripID, enum aStatus, String aStartLocation, String aEndLocation, String aInfo, Vehicle aVehicle, Trips aTrips)
  {
    return new ATrip(aPickupTime, aCost, aDate, aTripID, aStatus, aStartLocation, aEndLocation, aInfo, aVehicle, aTrips, this);
  }

  public boolean addATrip(ATrip aATrip)
  {
    boolean wasAdded = false;
    if (aTrips.contains(aATrip)) { return false; }
    Driver existingDriver = aATrip.getDriver();
    boolean isNewDriver = existingDriver != null && !this.equals(existingDriver);
    if (isNewDriver)
    {
      aATrip.setDriver(this);
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
    //Unable to remove aATrip, as it must always have a driver
    if (!this.equals(aATrip.getDriver()))
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
    Vehicle existingVehicle = vehicle;
    vehicle = null;
    if (existingVehicle != null)
    {
      existingVehicle.delete();
    }
    for(int i=aTrips.size(); i > 0; i--)
    {
      ATrip aATrip = aTrips.get(i - 1);
      aATrip.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "rating" + "=" + (getRating() != null ? !getRating().equals(this)  ? getRating().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "vehicle = "+(getVehicle()!=null?Integer.toHexString(System.identityHashCode(getVehicle())):"null");
  }
}