/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4201.5b72717f7 modeling language!*/


import java.util.*;

// line 43 "model.ump"
// line 129 "model.ump"
public class Trips
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Trips Associations
  private List<Administrator> administrators;
  private List<ATrip> aTrips;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Trips()
  {
    administrators = new ArrayList<Administrator>();
    aTrips = new ArrayList<ATrip>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Administrator getAdministrator(int index)
  {
    Administrator aAdministrator = administrators.get(index);
    return aAdministrator;
  }

  public List<Administrator> getAdministrators()
  {
    List<Administrator> newAdministrators = Collections.unmodifiableList(administrators);
    return newAdministrators;
  }

  public int numberOfAdministrators()
  {
    int number = administrators.size();
    return number;
  }

  public boolean hasAdministrators()
  {
    boolean has = administrators.size() > 0;
    return has;
  }

  public int indexOfAdministrator(Administrator aAdministrator)
  {
    int index = administrators.indexOf(aAdministrator);
    return index;
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
  public static int minimumNumberOfAdministrators()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addAdministrator(Administrator aAdministrator)
  {
    boolean wasAdded = false;
    if (administrators.contains(aAdministrator)) { return false; }
    administrators.add(aAdministrator);
    if (aAdministrator.indexOfTrip(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aAdministrator.addTrip(this);
      if (!wasAdded)
      {
        administrators.remove(aAdministrator);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeAdministrator(Administrator aAdministrator)
  {
    boolean wasRemoved = false;
    if (!administrators.contains(aAdministrator))
    {
      return wasRemoved;
    }

    int oldIndex = administrators.indexOf(aAdministrator);
    administrators.remove(oldIndex);
    if (aAdministrator.indexOfTrip(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aAdministrator.removeTrip(this);
      if (!wasRemoved)
      {
        administrators.add(oldIndex,aAdministrator);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAdministratorAt(Administrator aAdministrator, int index)
  {  
    boolean wasAdded = false;
    if(addAdministrator(aAdministrator))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAdministrators()) { index = numberOfAdministrators() - 1; }
      administrators.remove(aAdministrator);
      administrators.add(index, aAdministrator);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAdministratorAt(Administrator aAdministrator, int index)
  {
    boolean wasAdded = false;
    if(administrators.contains(aAdministrator))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAdministrators()) { index = numberOfAdministrators() - 1; }
      administrators.remove(aAdministrator);
      administrators.add(index, aAdministrator);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAdministratorAt(aAdministrator, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfATrips()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ATrip addATrip(int aPickupTime, double aCost, String aDate, int aTripID, enum aStatus, String aStartLocation, String aEndLocation, String aInfo, Vehicle aVehicle, Driver aDriver)
  {
    return new ATrip(aPickupTime, aCost, aDate, aTripID, aStatus, aStartLocation, aEndLocation, aInfo, aVehicle, this, aDriver);
  }

  public boolean addATrip(ATrip aATrip)
  {
    boolean wasAdded = false;
    if (aTrips.contains(aATrip)) { return false; }
    Trips existingTrips = aATrip.getTrips();
    boolean isNewTrips = existingTrips != null && !this.equals(existingTrips);
    if (isNewTrips)
    {
      aATrip.setTrips(this);
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
    //Unable to remove aATrip, as it must always have a trips
    if (!this.equals(aATrip.getTrips()))
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
    ArrayList<Administrator> copyOfAdministrators = new ArrayList<Administrator>(administrators);
    administrators.clear();
    for(Administrator aAdministrator : copyOfAdministrators)
    {
      aAdministrator.removeTrip(this);
    }
    for(int i=aTrips.size(); i > 0; i--)
    {
      ATrip aATrip = aTrips.get(i - 1);
      aATrip.delete();
    }
  }

}