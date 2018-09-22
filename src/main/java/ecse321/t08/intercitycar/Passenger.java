/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4201.5b72717f7 modeling language!*/


import java.util.*;

// line 28 "model.ump"
// line 117 "model.ump"
public class Passenger extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Passenger Attributes
  private int ridesTaken;

  //Passenger Associations
  private List<RideRequest> rideRequests;
  private List<Feedback> feedbacks;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Passenger(int aRidesTaken)
  {
    super();
    ridesTaken = aRidesTaken;
    rideRequests = new ArrayList<RideRequest>();
    feedbacks = new ArrayList<Feedback>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRidesTaken(int aRidesTaken)
  {
    boolean wasSet = false;
    ridesTaken = aRidesTaken;
    wasSet = true;
    return wasSet;
  }

  public int getRidesTaken()
  {
    return ridesTaken;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRideRequests()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addRideRequest(RideRequest aRideRequest)
  {
    boolean wasAdded = false;
    if (rideRequests.contains(aRideRequest)) { return false; }
    rideRequests.add(aRideRequest);
    if (aRideRequest.indexOfPassenger(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aRideRequest.addPassenger(this);
      if (!wasAdded)
      {
        rideRequests.remove(aRideRequest);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeRideRequest(RideRequest aRideRequest)
  {
    boolean wasRemoved = false;
    if (!rideRequests.contains(aRideRequest))
    {
      return wasRemoved;
    }

    int oldIndex = rideRequests.indexOf(aRideRequest);
    rideRequests.remove(oldIndex);
    if (aRideRequest.indexOfPassenger(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aRideRequest.removePassenger(this);
      if (!wasRemoved)
      {
        rideRequests.add(oldIndex,aRideRequest);
      }
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
  /* Code from template association_AddManyToOne */
  public Feedback addFeedback(String aReview, iint aScore)
  {
    return new Feedback(aReview, aScore, this);
  }

  public boolean addFeedback(Feedback aFeedback)
  {
    boolean wasAdded = false;
    if (feedbacks.contains(aFeedback)) { return false; }
    Passenger existingPassenger = aFeedback.getPassenger();
    boolean isNewPassenger = existingPassenger != null && !this.equals(existingPassenger);
    if (isNewPassenger)
    {
      aFeedback.setPassenger(this);
    }
    else
    {
      feedbacks.add(aFeedback);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeFeedback(Feedback aFeedback)
  {
    boolean wasRemoved = false;
    //Unable to remove aFeedback, as it must always have a passenger
    if (!this.equals(aFeedback.getPassenger()))
    {
      feedbacks.remove(aFeedback);
      wasRemoved = true;
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

  public void delete()
  {
    ArrayList<RideRequest> copyOfRideRequests = new ArrayList<RideRequest>(rideRequests);
    rideRequests.clear();
    for(RideRequest aRideRequest : copyOfRideRequests)
    {
      if (aRideRequest.numberOfPassengers() <= RideRequest.minimumNumberOfPassengers())
      {
        aRideRequest.delete();
      }
      else
      {
        aRideRequest.removePassenger(this);
      }
    }
    for(int i=feedbacks.size(); i > 0; i--)
    {
      Feedback aFeedback = feedbacks.get(i - 1);
      aFeedback.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "ridesTaken" + ":" + getRidesTaken()+ "]";
  }
}