package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.User;
import ecse321.t08.rideshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        User user = repository.createUser(userName, getStatus, emailAddress, name, password);
        if(user!=null) {
            return "User " + userName + " created.";
        } else {
            return "User " + userName + " could not be created, select a new username and make sure your email has not been used before.";
        }
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
    public User getUser(@PathVariable("id") int id) {
        User user = repository.getUser(id);
        if(user == null) {
            System.out.println("NOT FOUND");
        }
        return user;
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
    


