package ecse321.t08.rideshare.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ecse321.t08.rideshare.Entity.User;
import ecse321.t08.rideshare.Repository.UserRepository;

@RestController
@RequestMapping("api/user")
public class UserController{

    @Autowired
    UserRepository repository;



    @RequestMapping(value="/createUser", method=RequestMethod.POST)
    @ResponseBody
    public String createUser(String userName, boolean getStatus, String emailAddress, String name, String password){
        User newUser = repository.createUser(userName, getStatus, emailAddress, name, password);
        return "User " + userName + " created!";
    }
}
    


