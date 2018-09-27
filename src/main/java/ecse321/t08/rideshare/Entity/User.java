package ecse321.t08.rideshare.Entity;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;



@Entity
@Table(name = "users")
public class User {
private Set<UserRole> userRole;
   

    private int userID;
    private String userName;
    private boolean isUserActive = false;
    private String emailAddress;
    private String fullName;
    private String password;

    
  


    
   public void setUserRole(Set<UserRole> value) {
      this.userRole = value;
   }

   @ManyToMany
   @JoinTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
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
   
   
   public void setFullName(String value) {
      this.fullName = value;
   }
   
   public String getFullName() {
      return this.fullName;
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
