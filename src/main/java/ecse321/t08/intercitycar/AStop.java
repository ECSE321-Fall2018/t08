/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4201.5b72717f7 modeling language!*/



// line 92 "model.ump"
// line 165 "model.ump"
public class AStop
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AStop Attributes
  private String location;

  //AStop Associations
  private ATrip aTrip;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AStop(String aLocation, ATrip aATrip)
  {
    location = aLocation;
    boolean didAddATrip = setATrip(aATrip);
    if (!didAddATrip)
    {
      throw new RuntimeException("Unable to create aStop due to aTrip");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLocation(String aLocation)
  {
    boolean wasSet = false;
    location = aLocation;
    wasSet = true;
    return wasSet;
  }

  public String getLocation()
  {
    return location;
  }
  /* Code from template association_GetOne */
  public ATrip getATrip()
  {
    return aTrip;
  }
  /* Code from template association_SetOneToMany */
  public boolean setATrip(ATrip aATrip)
  {
    boolean wasSet = false;
    if (aATrip == null)
    {
      return wasSet;
    }

    ATrip existingATrip = aTrip;
    aTrip = aATrip;
    if (existingATrip != null && !existingATrip.equals(aATrip))
    {
      existingATrip.removeAStop(this);
    }
    aTrip.addAStop(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ATrip placeholderATrip = aTrip;
    this.aTrip = null;
    if(placeholderATrip != null)
    {
      placeholderATrip.removeAStop(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "location" + ":" + getLocation()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "aTrip = "+(getATrip()!=null?Integer.toHexString(System.identityHashCode(getATrip())):"null");
  }
}