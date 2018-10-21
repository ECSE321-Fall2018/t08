package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.User;
import ecse321.t08.rideshare.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    UserRepository repository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createUser(
        @RequestParam("username") String userName,
        @RequestParam("email") String emailAddress,
        @RequestParam("fullname") String fullname,
        @RequestParam("role") String role,
        @RequestParam("password") String password
    ) {
        User user = repository.createUser(userName, emailAddress, fullname, role, password);
        if (user != null) {
            JSONObject json = new JSONObject();
            json.put("data", role + " " + userName + " created!");
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            JSONObject json = new JSONObject();
            json.put("data", role + " " + userName + " could not be created.");
            return new ResponseEntity<>(json, HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateUser(
        @RequestParam("username") String userName,
        @RequestParam(value = "email", required = false) String emailAddress,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "role", required = false) String role,
        @RequestParam("oldpass") String oldpassword,
        @RequestParam(value="newpass", required = false) String newpassword) {
        if (emailAddress == null) {
            emailAddress = "";
        }
        if (name == null) {
            name = "";
        }
        if (role == null) {
            role = "";
        }
        if (newpassword == null) {
            newpassword = "";
        }
        User user = repository.updateUser(userName, emailAddress, name, role, oldpassword, newpassword);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> authenticateUser(
        @RequestParam("username") String userName,
        @RequestParam("password") String password
    ) {
        JSONObject json = new JSONObject();
        json.put("data",repository.authenticateUser(userName, password));
        return new ResponseEntity<>(json , HttpStatus.OK);
    }

    //Returns role
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> login(
            @RequestParam("username") String userName,
            @RequestParam("password") String password
    ) {
        JSONObject json = new JSONObject();
        json.put("data",repository.login(userName, password));
        return new ResponseEntity<>(json , HttpStatus.OK);
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> authorize(
            @RequestParam("username") String userName,
            @RequestParam("password") String password,
            @RequestParam("role") String role

            ) {
        JSONObject json = new JSONObject();
        json.put("data",repository.authorizeUser(userName, password, role));
        return new ResponseEntity<>(json , HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") int id) {
        User user = repository.getUser(id);
        if (user == null) {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> findUser(
        @RequestParam("adminusername") String adusername,
        @RequestParam("adminpass") String adpass,
        @RequestParam(value = "username", required = false) String userName,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "email", required = false) String emailAddress
    ) {
        if (userName == null) {
            userName = "";
        }
        if (name == null) {
            name = "";
        }
        if (emailAddress == null) {
            emailAddress = "";
        }

        List<User> userList = repository.findUser(adusername, adpass, userName, emailAddress, name);
        if (userList.isEmpty()) {
            return new ResponseEntity<>(userList, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @RequestMapping(value = "/userlist", method = RequestMethod.POST)
    public ResponseEntity<?> getUnfilteredUserList(
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        List<User> userlist = repository.getUnfilteredUserList(username, password);
        if(userlist.isEmpty()) {
            return new ResponseEntity<>(userlist, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userlist, HttpStatus.OK);
        }    }

    @RequestMapping(value = "/fuserlist", method = RequestMethod.POST)
    public ResponseEntity<?> getFilteredUserList(
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        List<User> userlist = repository.getFilteredUserList(username, password);
        if(userlist.isEmpty()) {
            return new ResponseEntity<>(userlist, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userlist, HttpStatus.OK);
        }
    }
}