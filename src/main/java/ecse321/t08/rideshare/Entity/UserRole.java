package ecse321.t08.rideshare.Entity;

import java.util.Set;

public class UserRole {
   private Set<User> user;
   
   public void setUser(Set<User> value) {
      this.user = value;
   }
   
   public Set<User> getUser() {
      return this.user;
   }
   
   }
