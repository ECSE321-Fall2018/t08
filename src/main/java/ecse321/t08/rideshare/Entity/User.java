package ecse321.t08.rideshare.Entity;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;



@Entity
@Table(name = "users")
@NamedQuery(name="User.findAll", query="SELECT e FROM users e")
public class User {


   private Set<UserRole> userRole;
   private int userID;
   private String userName;
   private boolean isUserActive = false;
   private String emailAddress;
   private String fullName;
   private String password;


   public User() {
   }
/*
   public User(User user) {
      this.isUserActive = user.getStatus();
      this.emailAddress = user.getEmailAddress();
      this.userRole = user.getUserRole();
      this.fullName = user.getFullName();
      this.userName =user.getUserName();
      this.userID = user.getUserID();
      this.password = user.getPassword();
   }
*/


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


   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
   public Set<UserRole> getUserRole() {
      return this.userRole;
   }

   public void setUserRoles(Set<UserRole> userRole)  {
      this.userRole = userRole;
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
