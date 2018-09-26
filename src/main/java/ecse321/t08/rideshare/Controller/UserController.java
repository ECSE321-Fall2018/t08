package ecse321.t08.rideshare.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("api/user")
public class UserController{

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public void createUser(){
        //Must fill in
    }
    

}
