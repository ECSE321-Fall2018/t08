/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4201.5b72717f7 modeling language!*/


import java.util.*;

// line 47 "model.ump"
// line 134 "model.ump"
public class ATrip
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ATrip Attributes
  private int pickupTime;
  private double cost;
  private String date;
  private int tripID;
  private enum status;
  private String startLocation;
  private String endLocation;
  private String info;

  //ATrip Associations
  private Vehicle vehicle;
  private List<RideRequest> rideRequests;
  private List<Feedback> feedbacks;
  private Trips trips;
  private Driver driver;
  private List<AStop> aStops;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ATrip(int aPickupTime, double aCost, String aDate, int aTripID, enum aStatus, String aStartLocation, String aEndLocation, String aInfo, Vehicle aVehicle, Trips aTrips, Driver aDriver)
  {
    pickupTime = aPickupTime;
    cost = aCost;
    date = aDate;
    tripID = aTripID;
    status = aStatus;
    startLocation = aStartLocation;
    endLocation = aEndLocation;
    info = aInfo;
    boolean didAddVehicle = setVehicle(aVehicle);
    if (!didAddVehicle)
    {
      throw new RuntimeException("Unable to create aTrip due to vehicle");
    }
    rideRequests = new ArrayList<RideRequest>();
    feedbacks = new ArrayList<Feedback>();
    boolean didAddTrips = setTrips(aTrips);
    if (!didAddTrips)
    {
      throw new RuntimeException("Unable to create aTrip due to trips");
    }
    boolean didAddDriver = setDriver(aDriver);
    if (!didAddDriver)
    {
      throw new RuntimeException("Unable to create aTrip due to driver");
    }
    aStops = new ArrayList<AStop>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPickupTime(int aPickupTime)
  {
    boolean wasSet = false;
    pickupTime = aPickupTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setCost(double aCost)
  {
    boolean wasSet = false;
    cost = aCost;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(String aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTripID(int aTripID)
  {
    boolean wasSet = false;
    tripID = aTripID;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(enum aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartLocation(String aStartLocation)
  {
    boolean wasSet = false;
    startLocation = aStartLocation;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndLocation(String aEndLocation)
  {
    boolean wasSet = false;
    endLocation = aEndLocation;
    wasSet = true;
    return wasSet;
  }

  public boolean setInfo(String aInfo)
  {
    boolean wasSet = false;
    info = aInfo;
    wasSet = true;
    return wasSet;
  }

  public int getPickupTime()
  {
    return pickupTime;
  }

  public double getCost()
  {
    return cost;
  }

  public String getDate()
  {
    return date;
  }

  public int getTripID()
  {
    return tripID;
  }

  public enum getStatus()
  {
    return status;
  }

  public String getStartLocation()
  {
    return startLocation;
  }

  public String getEndLocation()
  {
    return endLocation;
  }

  public String getInfo()
  {
    return info;
  }
  /* Code from template association_GetOne */
  public Vehicle getVehicle()
  {
    return vehicle;
  }
  /* Code from template association_GetMany */
  public RideRequest getRideRequest(int index)
  {
    RideRequest aRideRequest = rideRequests.get(index);
    return aRideRequest;
  }

  public List<RideRequest> getRideRequests()
  {
    List<RideRequest> newRideRequests = Collections.unmodifiableList(rideRequests);
    return newRideRequests;
  }

  public int numberOfRideRequests()
  {
    int number = rideRequests.size();
    return number;
  }

  public boolean hasRideRequests()
  {
    boolean has = rideRequests.size() > 0;
    return has;
  }

  public int indexOfRideRequest(RideRequest aRideRequest)
  {
    int index = rideRequests.indexOf(aRideRequest);
    return index;
  }
  /* Code from template association_GetMany */
  public Feedback getFeedback(int index)
  {
    Feedback aFeedback = feedbacks.get(index);
    return aFeedback;
  }

  public List<Feedback> getFeedbacks()
  {
    List<Feedback> newFeedbacks = Collections.unmodifiableList(feedbacks);
    return newFeedbacks;
  }

  public int numberOfFeedbacks()
  {
    int number = feedbacks.size();
    return number;
  }

  public boolean hasFeedbacks()
  {
    boolean has = feedbacks.size() > 0;
    return has;
  }

  public int indexOfFeedback(Feedback aFeedback)
  {
    int index = feedbacks.indexOf(aFeedback);
    return index;
  }
  /* Code from template association_GetOne */
  public Trips getTrips()
  {
    return trips;
  }
  /* Code from template association_GetOne */
  public Driver getDriver()
  {
    return driver;
  }
  /* Code from template association_GetMany */
  public AStop getAStop(int index)
  {
    AStop aAStop = aStops.get(index);
    return aAStop;
  }

  public List<AStop> getAStops()
  {
    List<AStop> newAStops = Collections.unmodifiableList(aStops);
    return newAStops;
  }

  public int numberOfAStops()
  {
    int number = aStops.size();
    return number;
  }

  public boolean hasAStops()
  {
    boolean has = aStops.size() > 0;
    return has;
  }

  public int indexOfAStop(AStop aAStop)
  {
    int index = aStops.indexOf(aAStop);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setVehicle(Vehicle aVehicle)
  {
    boolean wasSet = false;
    if (aVehicle == null)
    {
      return wasSet;
    }

    Vehicle existingVehicle = vehicle;
    vehicle = aVehicle;
    if (existingVehicle != null && !existingVehicle.equals(aVehicle))
    {
      existingVehicle.removeATrip(this);
    }
    vehicle.addATrip(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRideRequests()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfRideRequests()
  {
    return 4;
  }
  /* Code from template association_AddOptionalNToOne */
  public RideRequest addRideRequest(int aRequestID, boolean aIsAccepted, int aFromUserID, int aToUserID, Passenger... allPassengers)
  {
    if (numberOfRideRequests() >= maximumNumberOfRideRequests())
    {
      return null;
    }
    else
    {
      return new RideRequest(aRequestID, aIsAccepted, aFromUserID, aToUserID, this, allPassengers);
    }
  }

  public boolean addRideRequest(RideRequest aRideRequest)
  {
    boolean wasAdded = false;
    if (rideRequests.contains(aRideRequest)) { return false; }
    if (numberOfRideRequests() >= maximumNumberOfRideRequests())
    {
      return wasAdded;
    }

    ATrip existingATrip = aRideRequest.getATrip();
    boolean isNewATrip = existingATrip != null && !this.equals(existingATrip);
    if (isNewATrip)
    {
      aRideRequest.setATrip(this);
    }
    else
    {
      rideRequests.add(aRideRequest);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRideRequest(RideRequest aRideRequest)
  {
    boolean wasRemoved = false;
    //Unable to remove aRideRequest, as it must always have a aTrip
    if (!this.equals(aRideRequest.getATrip()))
    {
      rideRequests.remove(aRideRequest);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRideRequestAt(RideRequest aRideRequest, int index)
  {  
    boolean wasAdded = false;
    if(addRideRequest(aRideRequest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRideRequests()) { index = numberOfRideRequests() - 1; }
      rideRequests.remove(aRideRequest);
      rideRequests.add(index, aRideRequest);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRideRequestAt(RideRequest aRideRequest, int index)
  {
    boolean wasAdded = false;
    if(rideRequests.contains(aRideRequest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRideRequests()) { index = numberOfRideRequests() - 1; }
      rideRequests.remove(aRideRequest);
      rideRequests.add(index, aRideRequest);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRideRequestAt(aRideRequest, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfFeedbacks()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addFeedback(Feedback aFeedback)
  {
    boolean wasAdded = false;
    if (feedbacks.contains(aFeedback)) { return false; }
    feedbacks.add(aFeedback);
    if (aFeedback.indexOfATrip(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aFeedback.addATrip(this);
      if (!wasAdded)
      {
        feedbacks.remove(aFeedback);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeFeedback(Feedback aFeedback)
  {
    boolean wasRemoved = false;
    if (!feedbacks.contains(aFeedback))
    {
      return wasRemoved;
    }

    int oldIndex = feedbacks.indexOf(aFeedback);
    feedbacks.remove(oldIndex);
    if (aFeedback.indexOfATrip(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aFeedback.removeATrip(this);
      if (!wasRemoved)
      {
        feedbacks.add(oldIndex,aFeedback);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addFeedbackAt(Feedback aFeedback, int index)
  {  
    boolean wasAdded = false;
    if(addFeedback(aFeedback))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfFeedbacks()) { index = numberOfFeedbacks() - 1; }
      feedbacks.remove(aFeedback);
      feedbacks.add(index, aFeedback);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveFeedbackAt(Feedback aFeedback, int index)
  {
    boolean wasAdded = false;
    if(feedbacks.contains(aFeedback))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfFeedbacks()) { index = numberOfFeedbacks() - 1; }
      feedbacks.remove(aFeedback);
      feedbacks.add(index, aFeedback);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addFeedbackAt(aFeedback, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTrips(Trips aTrips)
  {
    boolean wasSet = false;
    if (aTrips == null)
    {
      return wasSet;
    }

    Trips existingTrips = trips;
    trips = aTrips;
    if (existingTrips != null && !existingTrips.equals(aTrips))
    {
      existingTrips.removeATrip(this);
    }
    trips.addATrip(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setDriver(Driver aDriver)
  {
    boolean wasSet = false;
    if (aDriver == null)
    {
      return wasSet;
    }

    Driver existingDriver = driver;
    driver = aDriver;
    if (existingDriver != null && !existingDriver.equals(aDriver))
    {
      existingDriver.removeATrip(this);
    }
    driver.addATrip(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAStops()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public AStop addAStop(String aLocation)
  {
    return new AStop(aLocation, this);
  }

  public boolean addAStop(AStop aAStop)
  {
    boolean wasAdded = false;
    if (aStops.contains(aAStop)) { return false; }
    ATrip existingATrip = aAStop.getATrip();
    boolean isNewATrip = existingATrip != null && !this.equals(existingATrip);
    if (isNewATrip)
    {
      aAStop.setATrip(this);
    }
    else
    {
      aStops.add(aAStop);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAStop(AStop aAStop)
  {
    boolean wasRemoved = false;
    //Unable to remove aAStop, as it must always have a aTrip
    if (!this.equals(aAStop.getATrip()))
    {
      aStops.remove(aAStop);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAStopAt(AStop aAStop, int index)
  {  
    boolean wasAdded = false;
    if(addAStop(aAStop))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAStops()) { index = numberOfAStops() - 1; }
      aStops.remove(aAStop);
      aStops.add(index, aAStop);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAStopAt(AStop aAStop, int index)
  {
    boolean wasAdded = false;
    if(aStops.contains(aAStop))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAStops()) { index = numberOfAStops() - 1; }
      aStops.remove(aAStop);
      aStops.add(index, aAStop);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAStopAt(aAStop, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Vehicle placeholderVehicle = vehicle;
    this.vehicle = null;
    if(placeholderVehicle != null)
    {
      placeholderVehicle.removeATrip(this);
    }
    for(int i=rideRequests.size(); i > 0; i--)
    {
      RideRequest aRideRequest = rideRequests.get(i - 1);
      aRideRequest.delete();
    }
    ArrayList<Feedback> copyOfFeedbacks = new ArrayList<Feedback>(feedbacks);
    feedbacks.clear();
    for(Feedback aFeedback : copyOfFeedbacks)
    {
      aFeedback.removeATrip(this);
    }
    Trips placeholderTrips = trips;
    this.trips = null;
    if(placeholderTrips != null)
    {
      placeholderTrips.removeATrip(this);
    }
    Driver placeholderDriver = driver;
    this.driver = null;
    if(placeholderDriver != null)
    {
      placeholderDriver.removeATrip(this);
    }
    for(int i=aStops.size(); i > 0; i--)
    {
      AStop aAStop = aStops.get(i - 1);
      aAStop.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "pickupTime" + ":" + getPickupTime()+ "," +
            "cost" + ":" + getCost()+ "," +
            "date" + ":" + getDate()+ "," +
            "tripID" + ":" + getTripID()+ "," +
            "startLocation" + ":" + getStartLocation()+ "," +
            "endLocation" + ":" + getEndLocation()+ "," +
            "info" + ":" + getInfo()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "vehicle = "+(getVehicle()!=null?Integer.toHexString(System.identityHashCode(getVehicle())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "trips = "+(getTrips()!=null?Integer.toHexString(System.identityHashCode(getTrips())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "driver = "+(getDriver()!=null?Integer.toHexString(System.identityHashCode(getDriver())):"null");
  }
}