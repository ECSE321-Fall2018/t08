/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4201.5b72717f7 modeling language!*/


import java.util.*;

// line 35 "model.ump"
// line 123 "model.ump"
public class Administrator extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Administrator Associations
  private List<Trips> trips;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Administrator()
  {
    super();
    trips = new ArrayList<Trips>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Trips getTrip(int index)
  {
    Trips aTrip = trips.get(index);
    return aTrip;
  }

  public List<Trips> getTrips()
  {
    List<Trips> newTrips = Collections.unmodifiableList(trips);
    return newTrips;
  }

  public int numberOfTrips()
  {
    int number = trips.size();
    return number;
  }

  public boolean hasTrips()
  {
    boolean has = trips.size() > 0;
    return has;
  }

  public int indexOfTrip(Trips aTrip)
  {
    int index = trips.indexOf(aTrip);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTrips()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addTrip(Trips aTrip)
  {
    boolean wasAdded = false;
    if (trips.contains(aTrip)) { return false; }
    trips.add(aTrip);
    if (aTrip.indexOfAdministrator(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTrip.addAdministrator(this);
      if (!wasAdded)
      {
        trips.remove(aTrip);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeTrip(Trips aTrip)
  {
    boolean wasRemoved = false;
    if (!trips.contains(aTrip))
    {
      return wasRemoved;
    }

    int oldIndex = trips.indexOf(aTrip);
    trips.remove(oldIndex);
    if (aTrip.indexOfAdministrator(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTrip.removeAdministrator(this);
      if (!wasRemoved)
      {
        trips.add(oldIndex,aTrip);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTripAt(Trips aTrip, int index)
  {  
    boolean wasAdded = false;
    if(addTrip(aTrip))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrips()) { index = numberOfTrips() - 1; }
      trips.remove(aTrip);
      trips.add(index, aTrip);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTripAt(Trips aTrip, int index)
  {
    boolean wasAdded = false;
    if(trips.contains(aTrip))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrips()) { index = numberOfTrips() - 1; }
      trips.remove(aTrip);
      trips.add(index, aTrip);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTripAt(aTrip, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Trips> copyOfTrips = new ArrayList<Trips>(trips);
    trips.clear();
    for(Trips aTrip : copyOfTrips)
    {
      aTrip.removeAdministrator(this);
    }
    super.delete();
  }

}