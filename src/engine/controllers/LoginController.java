package engine.controllers;

import engine.descriptors.User;
import engine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/api/register")
    public void registerUser(@Valid @RequestBody User user) {
        String email = user.getEmail();
        if (userService.isRegisteredEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email address is already registered : " + email);
        }
        userService.registerUser(user);
    }
}
