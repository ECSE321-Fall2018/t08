/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4201.5b72717f7 modeling language!*/


import java.util.*;

// line 81 "model.ump"
// line 154 "model.ump"
public class Feedback
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Feedback Attributes
  private String review;
  private iint score;

  //Feedback Associations
  private Passenger passenger;
  private List<ATrip> aTrips;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Feedback(String aReview, iint aScore, Passenger aPassenger)
  {
    review = aReview;
    score = aScore;
    boolean didAddPassenger = setPassenger(aPassenger);
    if (!didAddPassenger)
    {
      throw new RuntimeException("Unable to create feedback due to passenger");
    }
    aTrips = new ArrayList<ATrip>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setReview(String aReview)
  {
    boolean wasSet = false;
    review = aReview;
    wasSet = true;
    return wasSet;
  }

  public boolean setScore(iint aScore)
  {
    boolean wasSet = false;
    score = aScore;
    wasSet = true;
    return wasSet;
  }

  public String getReview()
  {
    return review;
  }

  public iint getScore()
  {
    return score;
  }
  /* Code from template association_GetOne */
  public Passenger getPassenger()
  {
    return passenger;
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
  /* Code from template association_SetOneToMany */
  public boolean setPassenger(Passenger aPassenger)
  {
    boolean wasSet = false;
    if (aPassenger == null)
    {
      return wasSet;
    }

    Passenger existingPassenger = passenger;
    passenger = aPassenger;
    if (existingPassenger != null && !existingPassenger.equals(aPassenger))
    {
      existingPassenger.removeFeedback(this);
    }
    passenger.addFeedback(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfATrips()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addATrip(ATrip aATrip)
  {
    boolean wasAdded = false;
    if (aTrips.contains(aATrip)) { return false; }
    aTrips.add(aATrip);
    if (aATrip.indexOfFeedback(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aATrip.addFeedback(this);
      if (!wasAdded)
      {
        aTrips.remove(aATrip);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeATrip(ATrip aATrip)
  {
    boolean wasRemoved = false;
    if (!aTrips.contains(aATrip))
    {
      return wasRemoved;
    }

    int oldIndex = aTrips.indexOf(aATrip);
    aTrips.remove(oldIndex);
    if (aATrip.indexOfFeedback(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aATrip.removeFeedback(this);
      if (!wasRemoved)
      {
        aTrips.add(oldIndex,aATrip);
      }
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
    Passenger placeholderPassenger = passenger;
    this.passenger = null;
    if(placeholderPassenger != null)
    {
      placeholderPassenger.removeFeedback(this);
    }
    ArrayList<ATrip> copyOfATrips = new ArrayList<ATrip>(aTrips);
    aTrips.clear();
    for(ATrip aATrip : copyOfATrips)
    {
      aATrip.removeFeedback(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "review" + ":" + getReview()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "score" + "=" + (getScore() != null ? !getScore().equals(this)  ? getScore().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "passenger = "+(getPassenger()!=null?Integer.toHexString(System.identityHashCode(getPassenger())):"null");
  }
}