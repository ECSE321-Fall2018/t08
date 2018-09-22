/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4201.5b72717f7 modeling language!*/


import java.util.*;

// line 2 "model.ump"
// line 100 "model.ump"
public class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String username;
  private String emailAddress;
  private String password;
  private int userID;
  private enum status;
  private boolean isAdmin;
  private enum userType;
  private int age;
  private String name;
  private enum gender;

  //User Associations
  private List<UserRole> userRoles;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aUsername, String aEmailAddress, String aPassword, int aUserID, enum aStatus, boolean aIsAdmin, enum aUserType, int aAge, String aName, enum aGender, UserRole... allUserRoles)
  {
    username = aUsername;
    emailAddress = aEmailAddress;
    password = aPassword;
    userID = aUserID;
    status = aStatus;
    isAdmin = aIsAdmin;
    userType = aUserType;
    age = aAge;
    name = aName;
    gender = aGender;
    userRoles = new ArrayList<UserRole>();
    boolean didAddUserRoles = setUserRoles(allUserRoles);
    if (!didAddUserRoles)
    {
      throw new RuntimeException("Unable to create User, must have 1 to 3 userRoles");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmailAddress(String aEmailAddress)
  {
    boolean wasSet = false;
    emailAddress = aEmailAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setUserID(int aUserID)
  {
    boolean wasSet = false;
    userID = aUserID;
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

  public boolean setIsAdmin(boolean aIsAdmin)
  {
    boolean wasSet = false;
    isAdmin = aIsAdmin;
    wasSet = true;
    return wasSet;
  }

  public boolean setUserType(enum aUserType)
  {
    boolean wasSet = false;
    userType = aUserType;
    wasSet = true;
    return wasSet;
  }

  public boolean setAge(int aAge)
  {
    boolean wasSet = false;
    age = aAge;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setGender(enum aGender)
  {
    boolean wasSet = false;
    gender = aGender;
    wasSet = true;
    return wasSet;
  }

  public String getUsername()
  {
    return username;
  }

  public String getEmailAddress()
  {
    return emailAddress;
  }

  public String getPassword()
  {
    return password;
  }

  public int getUserID()
  {
    return userID;
  }

  public enum getStatus()
  {
    return status;
  }

  public boolean getIsAdmin()
  {
    return isAdmin;
  }

  public enum getUserType()
  {
    return userType;
  }

  public int getAge()
  {
    return age;
  }

  public String getName()
  {
    return name;
  }

  public enum getGender()
  {
    return gender;
  }
  /* Code from template association_GetMany */
  public UserRole getUserRole(int index)
  {
    UserRole aUserRole = userRoles.get(index);
    return aUserRole;
  }

  public List<UserRole> getUserRoles()
  {
    List<UserRole> newUserRoles = Collections.unmodifiableList(userRoles);
    return newUserRoles;
  }

  public int numberOfUserRoles()
  {
    int number = userRoles.size();
    return number;
  }

  public boolean hasUserRoles()
  {
    boolean has = userRoles.size() > 0;
    return has;
  }

  public int indexOfUserRole(UserRole aUserRole)
  {
    int index = userRoles.indexOf(aUserRole);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfUserRolesValid()
  {
    boolean isValid = numberOfUserRoles() >= minimumNumberOfUserRoles() && numberOfUserRoles() <= maximumNumberOfUserRoles();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUserRoles()
  {
    return 1;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfUserRoles()
  {
    return 3;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addUserRole(UserRole aUserRole)
  {
    boolean wasAdded = false;
    if (userRoles.contains(aUserRole)) { return false; }
    if (numberOfUserRoles() >= maximumNumberOfUserRoles())
    {
      return wasAdded;
    }

    userRoles.add(aUserRole);
    if (aUserRole.indexOfUser(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aUserRole.addUser(this);
      if (!wasAdded)
      {
        userRoles.remove(aUserRole);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMNToMany */
  public boolean removeUserRole(UserRole aUserRole)
  {
    boolean wasRemoved = false;
    if (!userRoles.contains(aUserRole))
    {
      return wasRemoved;
    }

    if (numberOfUserRoles() <= minimumNumberOfUserRoles())
    {
      return wasRemoved;
    }

    int oldIndex = userRoles.indexOf(aUserRole);
    userRoles.remove(oldIndex);
    if (aUserRole.indexOfUser(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aUserRole.removeUser(this);
      if (!wasRemoved)
      {
        userRoles.add(oldIndex,aUserRole);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMNToMany */
  public boolean setUserRoles(UserRole... newUserRoles)
  {
    boolean wasSet = false;
    ArrayList<UserRole> verifiedUserRoles = new ArrayList<UserRole>();
    for (UserRole aUserRole : newUserRoles)
    {
      if (verifiedUserRoles.contains(aUserRole))
      {
        continue;
      }
      verifiedUserRoles.add(aUserRole);
    }

    if (verifiedUserRoles.size() != newUserRoles.length || verifiedUserRoles.size() < minimumNumberOfUserRoles() || verifiedUserRoles.size() > maximumNumberOfUserRoles())
    {
      return wasSet;
    }

    ArrayList<UserRole> oldUserRoles = new ArrayList<UserRole>(userRoles);
    userRoles.clear();
    for (UserRole aNewUserRole : verifiedUserRoles)
    {
      userRoles.add(aNewUserRole);
      if (oldUserRoles.contains(aNewUserRole))
      {
        oldUserRoles.remove(aNewUserRole);
      }
      else
      {
        aNewUserRole.addUser(this);
      }
    }

    for (UserRole anOldUserRole : oldUserRoles)
    {
      anOldUserRole.removeUser(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserRoleAt(UserRole aUserRole, int index)
  {  
    boolean wasAdded = false;
    if(addUserRole(aUserRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserRoles()) { index = numberOfUserRoles() - 1; }
      userRoles.remove(aUserRole);
      userRoles.add(index, aUserRole);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserRoleAt(UserRole aUserRole, int index)
  {
    boolean wasAdded = false;
    if(userRoles.contains(aUserRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserRoles()) { index = numberOfUserRoles() - 1; }
      userRoles.remove(aUserRole);
      userRoles.add(index, aUserRole);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserRoleAt(aUserRole, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<UserRole> copyOfUserRoles = new ArrayList<UserRole>(userRoles);
    userRoles.clear();
    for(UserRole aUserRole : copyOfUserRoles)
    {
      aUserRole.removeUser(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "emailAddress" + ":" + getEmailAddress()+ "," +
            "password" + ":" + getPassword()+ "," +
            "userID" + ":" + getUserID()+ "," +
            "isAdmin" + ":" + getIsAdmin()+ "," +
            "age" + ":" + getAge()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "userType" + "=" + (getUserType() != null ? !getUserType().equals(this)  ? getUserType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "gender" + "=" + (getGender() != null ? !getGender().equals(this)  ? getGender().toString().replaceAll("  ","    ") : "this" : "null");
  }
}