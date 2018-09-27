package ecse321.t08.rideshare.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="roles")
public class UserRole {

   private Long id;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @ManyToMany(cascade = CascadeType.ALL)
   @JoinTable
   private Set<User> user;
   
   public void setUser(Set<User> value) {
      this.user = value;
   }

   @ManyToMany(mappedBy="userRole")
   public Set<User> getUser() {
      return this.user;
   }
   
   }
