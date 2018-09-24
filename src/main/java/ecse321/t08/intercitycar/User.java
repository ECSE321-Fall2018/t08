import java.util.Set;
import java.util.HashSet;

public class User {
private Set<UserRole> userRole;
   
   public void setUserRole(Set<UserRole> value) {
      this.userRole = value;
   }
   
   public Set<UserRole> getUserRole() {
      return this.userRole;
   }
   
   
   private String userName;
   
   public void setUserName(String value) {
      this.userName = value;
   }
   
   public String getUserName() {
      return this.userName;
   }
   
   private String emailAddress;
   
   public void setEmailAddress(String value) {
      this.emailAddress = value;
   }
   
   public String getEmailAddress() {
      return this.emailAddress;
   }
   
   private int password;
   
   public void setPassword(int value) {
      this.password = value;
   }
   
   public int getPassword() {
      return this.password;
   }
   
   private int userID;
   
   public void setUserID(int value) {
      this.userID = value;
   }
   
   public int getUserID() {
      return this.userID;
   }
   
   private enum status {
	   INACTIVE, ACTIVE
   }
   
   public void setStatus(enum value) {
      this.status = value;
   }
   
   public status getStatus() {
      return this.status;
   }
   
   private String name;
   
   public void setName(String value) {
      this.name = value;
   }
   
   public String getName() {
      return this.name;
   }
   
   public void logIn() {
      // TODO implement this operation
      throw new UnsupportedOperationException("not implemented");
   }
   
   public void logOut() {
      // TODO implement this operation
      throw new UnsupportedOperationException("not implemented");
   }
   
   }
