package ecse321.t08.rideshare.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ecse321.t08.rideshare.Entity.User;
import ecse321.t08.rideshare.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController{

    @Autowired
    UserRepository repository;
    
    @RequestMapping(value="/createUser", method=RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestParam(value="username", required=true) String userName,
                             @RequestParam(value="status", required=true) boolean getStatus,
                             @RequestParam(value="email", required=true) String emailAddress,
                             @RequestParam(value="name", required=true) String name,
                             @RequestParam(value="password", required=true) String password) {
        User newUser = repository.createUser(userName, getStatus, emailAddress, name, password);
        return "User " + userName + " created!";
    }

    @RequestMapping(value="/updateUser", method=RequestMethod.POST)
    @ResponseBody
    public User updateUser(@RequestParam(value="username", required=true) String userName,
                             @RequestParam(value="status", required=false) boolean getStatus,
                             @RequestParam(value="email", required=false) String emailAddress,
                             @RequestParam(value="name", required=false) String name,
                             @RequestParam(value="password", required=true) String password) {
        User user = repository.updateUser(userName, getStatus, emailAddress, name, password);
        return user;
    }

    @RequestMapping(value="/users/{id}", method=RequestMethod.GET)
    public String getUser(@PathVariable("id") int id) {
        User user = repository.getUser(id);
        if(user == null) {
            return "NOT FOUND";
        }
        return user.getUserName();
    }

    @RequestMapping(value="/find", method=RequestMethod.POST)
    @ResponseBody
    public List<User> findUser(@RequestParam(value="username", required=true) String userName,
                               @RequestParam(value="name", required=true) String name,
                               @RequestParam(value="email", required=true) String emailAddress) {
        List<User> userList = repository.findUser(userName, emailAddress, name);
        if(userList.isEmpty()) {
            System.out.println("NOT FOUND");
        }

        return userList;
    }


}
    


