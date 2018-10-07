package ecse321.t08.rideshare.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT e FROM User e")
})
public class User {
    private int userID;
    private String username;
    private boolean isUserActive = false;
    private String emailAddress;
    private String fullName;
    private String password;
    private int tripnumber;
    private String role; // Either Driver, Passenger or Administrator (case-sensitive!)

    public User() {
    }

    public User(User user) {
        this.isUserActive = user.getStatus();
        this.emailAddress = user.getEmailAddress();
        this.fullName = user.getFullName();
        this.username = user.getUsername();
        this.userID = user.getUserID();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.tripnumber = user.getTripnumber();
    }

    public void setUserID(int value) {
        this.userID = value;
    }

    @Id
    @Column(name = "userid")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getUserID() {
        return this.userID;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    @Column(name = "username")
    public String getUsername() {
        return this.username;
    }

    public void setEmailAddress(String value) {
        this.emailAddress = value;
    }

    @Column(name = "email")
    public String getEmailAddress() {
        return this.emailAddress;
    }


    public void setPassword(String value) {
        this.password = value;
    }

    @Column(name = "password")
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

    @Column(name = "fullname")
    public String getFullName() {
        return this.fullName;
    }

    public void setRole(String value) {
        this.role = value;
    }

    @Column(name = "role")
    public String getRole() {
        return this.role;
    }

    @Column(name = "tripnumber")
    public int getTripnumber() {
        return tripnumber;
    }

    public void setTripNumber(int tripnumber) {
        this.tripnumber = tripnumber;
    }
}
