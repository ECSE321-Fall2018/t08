package ecse321.t08.rideshare.Entity;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;



@Entity
@Table(name = "user")
public class User {
private Set<UserRole> userRole;
   

    private int userID;
    private boolean isUserActive = false;
    private String userName;
    private String emailAddress;
    private String password;

    
    private String name;


    
   public void setUserRole(Set<UserRole> value) {
      this.userRole = value;
   }
   
   public Set<UserRole> getUserRole() {
      return this.userRole;
   }
   
   public void setUserName(String value) {
      this.userName = value;
   }
   
   public String getUserName() {
      return this.userName;
   }
    
   public void setEmailAddress(String value) {
      this.emailAddress = value;
   }
   
   public String getEmailAddress() {
      return this.emailAddress;
   }
   
   
   public void setPassword(String value) {
      this.password = value;
   }
   
   public String getPassword() {
      return this.password;
   }
   
   
   public void setUserID(int value) {
      this.userID = value;
   }
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   public int getUserID() {
      return this.userID;
   }
   

   
   public void setStatus(boolean value) {
      this.isUserActive = value;
   }
   
   public boolean getStatus() {
      return this.isUserActive;
   }
   
   
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
