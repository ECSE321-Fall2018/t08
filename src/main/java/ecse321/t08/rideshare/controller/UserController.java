package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.User;
import ecse321.t08.rideshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    UserRepository repository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(
        @RequestParam("username") String username,
        @RequestParam("email") String emailAddress,
        @RequestParam("name") String fullName,
        @RequestParam("role") String role,
        @RequestParam("password") String password
    ) {
        User user = repository.createUser(username, emailAddress, fullName, role, password);
        if (user != null) {
            return role + " " + username + " created.";
        } else {
            return role + " " + username + " could not be created, either the username or email is taken.";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public User updateUser(
        @RequestParam("username") String username,
        @RequestParam(value = "email", required = false) String emailAddress,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "role", required = false) String role,
        @RequestParam("oldPassword") String oldPassword,
        @RequestParam("newPassword") String newPassword
    ) {
        if (emailAddress == null) {
            emailAddress = "";
        }
        if (name == null) {
            name = "";
        }
        if (role == null) {
            role = "";
        }
        return repository.updateUser(username, emailAddress, name, role, oldPassword, newPassword);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ResponseBody
    public int authenticateUser(
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        return repository.authenticateUser(username, password);
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    @ResponseBody
    public int authorizeUser(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("role") String role
    ) {
        return repository.authorizeUser(username, password, role);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") int id) {
        User user = repository.getUser(id);
        if (user == null) {
            System.out.println("User not found.");
        }
        return user;
    }

    @RequestMapping(value = "/finduser", method = RequestMethod.POST)
    @ResponseBody
    public List<User> findUser(
        @RequestParam(value = "username", required = false) String username,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "email", required = false) String emailAddress
    ) {
        if (username == null) {
            username = "";
        }
        if (name == null) {
            name = "";
        }
        if (emailAddress == null) {
            emailAddress = "";
        }

        List<User> userList = repository.findUser(username, emailAddress, name);
        if (userList.isEmpty()) {
            System.out.println("User not found.");
        }
        return userList;
    }

    @RequestMapping(value = "/userlist", method = RequestMethod.POST)
    public List getUnfilteredUserList(
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        return repository.getUnfilteredUserList(username, password);
    }

    @RequestMapping(value = "/fuserlist", method = RequestMethod.POST)
    public List getFilteredUserList(
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        return repository.getFilteredUserList(username, password);
    }
}