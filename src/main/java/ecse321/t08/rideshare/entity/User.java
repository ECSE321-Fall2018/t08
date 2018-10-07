package ecse321.t08.rideshare.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT e FROM User e")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userid;

    private String username;
    private boolean isUserActive = false;
    private String emailAddress;
    private String fullName;
    private String password;
    private int tripnumber;
    private String role; // Either Driver, Passenger or Administrator (case-sensitive!)

    public User() {}

    public User(User user) {
        this.isUserActive = user.getStatus();
        this.emailAddress = user.getEmailAddress();
        this.fullName = user.getFullName();
        this.username = user.getUsername();
        this.userid = user.getUserId();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.tripnumber = user.getTripNumber();
    }

    public void setUserId(int value) {
        this.userid = value;
    }

    @Column(name = "userid")
    public int getUserId() {
        return this.userid;
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
    public int getTripNumber() {
        return tripnumber;
    }

    public void setTripNumber(int tripnumber) {
        this.tripnumber = tripnumber;
    }

    public void incrementTripNumber() {
        this.tripnumber++;
    }

}
