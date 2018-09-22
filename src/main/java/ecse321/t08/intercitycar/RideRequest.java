/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4201.5b72717f7 modeling language!*/


import java.util.*;

// line 73 "model.ump"
// line 149 "model.ump"
public class RideRequest
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RideRequest Attributes
  private int requestID;
  private boolean isAccepted;
  private int fromUserID;
  private int toUserID;

  //RideRequest Associations
  private List<Passenger> passengers;
  private ATrip aTrip;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RideRequest(int aRequestID, boolean aIsAccepted, int aFromUserID, int aToUserID, ATrip aATrip, Passenger... allPassengers)
  {
    requestID = aRequestID;
    isAccepted = aIsAccepted;
    fromUserID = aFromUserID;
    toUserID = aToUserID;
    passengers = new ArrayList<Passenger>();
    boolean didAddPassengers = setPassengers(allPassengers);
    if (!didAddPassengers)
    {
      throw new RuntimeException("Unable to create RideRequest, must have 1 to 4 passengers");
    }
    boolean didAddATrip = setATrip(aATrip);
    if (!didAddATrip)
    {
      throw new RuntimeException("Unable to create rideRequest due to aTrip");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRequestID(int aRequestID)
  {
    boolean wasSet = false;
    requestID = aRequestID;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsAccepted(boolean aIsAccepted)
  {
    boolean wasSet = false;
    isAccepted = aIsAccepted;
    wasSet = true;
    return wasSet;
  }

  public boolean setFromUserID(int aFromUserID)
  {
    boolean wasSet = false;
    fromUserID = aFromUserID;
    wasSet = true;
    return wasSet;
  }

  public boolean setToUserID(int aToUserID)
  {
    boolean wasSet = false;
    toUserID = aToUserID;
    wasSet = true;
    return wasSet;
  }

  public int getRequestID()
  {
    return requestID;
  }

  public boolean getIsAccepted()
  {
    return isAccepted;
  }

  public int getFromUserID()
  {
    return fromUserID;
  }

  public int getToUserID()
  {
    return toUserID;
  }
  /* Code from template association_GetMany */
  public Passenger getPassenger(int index)
  {
    Passenger aPassenger = passengers.get(index);
    return aPassenger;
  }

  public List<Passenger> getPassengers()
  {
    List<Passenger> newPassengers = Collections.unmodifiableList(passengers);
    return newPassengers;
  }

  public int numberOfPassengers()
  {
    int number = passengers.size();
    return number;
  }

  public boolean hasPassengers()
  {
    boolean has = passengers.size() > 0;
    return has;
  }

  public int indexOfPassenger(Passenger aPassenger)
  {
    int index = passengers.indexOf(aPassenger);
    return index;
  }
  /* Code from template association_GetOne */
  public ATrip getATrip()
  {
    return aTrip;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPassengersValid()
  {
    boolean isValid = numberOfPassengers() >= minimumNumberOfPassengers() && numberOfPassengers() <= maximumNumberOfPassengers();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPassengers()
  {
    return 1;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPassengers()
  {
    return 4;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addPassenger(Passenger aPassenger)
  {
    boolean wasAdded = false;
    if (passengers.contains(aPassenger)) { return false; }
    if (numberOfPassengers() >= maximumNumberOfPassengers())
    {
      return wasAdded;
    }

    passengers.add(aPassenger);
    if (aPassenger.indexOfRideRequest(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aPassenger.addRideRequest(this);
      if (!wasAdded)
      {
        passengers.remove(aPassenger);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMNToMany */
  public boolean removePassenger(Passenger aPassenger)
  {
    boolean wasRemoved = false;
    if (!passengers.contains(aPassenger))
    {
      return wasRemoved;
    }

    if (numberOfPassengers() <= minimumNumberOfPassengers())
    {
      return wasRemoved;
    }

    int oldIndex = passengers.indexOf(aPassenger);
    passengers.remove(oldIndex);
    if (aPassenger.indexOfRideRequest(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aPassenger.removeRideRequest(this);
      if (!wasRemoved)
      {
        passengers.add(oldIndex,aPassenger);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMNToMany */
  public boolean setPassengers(Passenger... newPassengers)
  {
    boolean wasSet = false;
    ArrayList<Passenger> verifiedPassengers = new ArrayList<Passenger>();
    for (Passenger aPassenger : newPassengers)
    {
      if (verifiedPassengers.contains(aPassenger))
      {
        continue;
      }
      verifiedPassengers.add(aPassenger);
    }

    if (verifiedPassengers.size() != newPassengers.length || verifiedPassengers.size() < minimumNumberOfPassengers() || verifiedPassengers.size() > maximumNumberOfPassengers())
    {
      return wasSet;
    }

    ArrayList<Passenger> oldPassengers = new ArrayList<Passenger>(passengers);
    passengers.clear();
    for (Passenger aNewPassenger : verifiedPassengers)
    {
      passengers.add(aNewPassenger);
      if (oldPassengers.contains(aNewPassenger))
      {
        oldPassengers.remove(aNewPassenger);
      }
      else
      {
        aNewPassenger.addRideRequest(this);
      }
    }

    for (Passenger anOldPassenger : oldPassengers)
    {
      anOldPassenger.removeRideRequest(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPassengerAt(Passenger aPassenger, int index)
  {  
    boolean wasAdded = false;
    if(addPassenger(aPassenger))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPassengers()) { index = numberOfPassengers() - 1; }
      passengers.remove(aPassenger);
      passengers.add(index, aPassenger);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePassengerAt(Passenger aPassenger, int index)
  {
    boolean wasAdded = false;
    if(passengers.contains(aPassenger))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPassengers()) { index = numberOfPassengers() - 1; }
      passengers.remove(aPassenger);
      passengers.add(index, aPassenger);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPassengerAt(aPassenger, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setATrip(ATrip aATrip)
  {
    boolean wasSet = false;
    //Must provide aTrip to rideRequest
    if (aATrip == null)
    {
      return wasSet;
    }

    //aTrip already at maximum (4)
    if (aATrip.numberOfRideRequests() >= ATrip.maximumNumberOfRideRequests())
    {
      return wasSet;
    }
    
    ATrip existingATrip = aTrip;
    aTrip = aATrip;
    if (existingATrip != null && !existingATrip.equals(aATrip))
    {
      boolean didRemove = existingATrip.removeRideRequest(this);
      if (!didRemove)
      {
        aTrip = existingATrip;
        return wasSet;
      }
    }
    aTrip.addRideRequest(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<Passenger> copyOfPassengers = new ArrayList<Passenger>(passengers);
    passengers.clear();
    for(Passenger aPassenger : copyOfPassengers)
    {
      aPassenger.removeRideRequest(this);
    }
    ATrip placeholderATrip = aTrip;
    this.aTrip = null;
    if(placeholderATrip != null)
    {
      placeholderATrip.removeRideRequest(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "requestID" + ":" + getRequestID()+ "," +
            "isAccepted" + ":" + getIsAccepted()+ "," +
            "fromUserID" + ":" + getFromUserID()+ "," +
            "toUserID" + ":" + getToUserID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "aTrip = "+(getATrip()!=null?Integer.toHexString(System.identityHashCode(getATrip())):"null");
  }
}