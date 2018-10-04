package ecse321.t08.rideshare.Entity;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;



@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.findUsername", query = "SELECT username FROM WHERE User username LIKE :usernameparam"),
        @NamedQuery(name = "User.findAll", query = "SELECT e FROM User e")
})
public class User {

   private int userID;
   private String userName;
   private boolean isUserActive = false;
   private String emailAddress;
   private String fullName;
   private String password;

   private String role; //Either Driver, Passenger or Administrator

   //Driver
   private String vehicle_id;

   public User() {
   }

   public User(User user) {
      this.isUserActive = user.getStatus();
      this.emailAddress = user.getEmailAddress();
      this.fullName = user.getFullName();
      this.userName =user.getUserName();
      this.userID = user.getUserID();
      this.password = user.getPassword();
   }


   public void setUserID(int value) {
      this.userID = value;
   }

   @Id
   @Column(name = "userid")
   @GeneratedValue(strategy = GenerationType.AUTO)
   public int getUserID() {
      return this.userID;
   }

   public void setUserName(String value) {
      this.userName = value;
   }

   @Column(name="username")
   public String getUserName() {
      return this.userName;
   }
    
   public void setEmailAddress(String value) {
      this.emailAddress = value;
   }

   @Column(name="email")
   public String getEmailAddress() {
      return this.emailAddress;
   }
   
   
   public void setPassword(String value) {
      this.password = value;
   }

   @Column(name="password")
   public String getPassword() {
      return this.password;
   }


   public void setStatus(boolean value) {
      this.isUserActive = value;
   }

   @Column(name = "userstatus")
   public boolean getStatus() {
      return this.isUserActive;
   }
   
   public void setFullName(String value) {
      this.fullName = value;
   }

   @Column(name="fullname")
   public String getFullName() {
      return this.fullName;
   }

   public void setRole(String value) { this.role = value; }

   @Column (name="role")
   public String getRole() { return this.role; }

   }
