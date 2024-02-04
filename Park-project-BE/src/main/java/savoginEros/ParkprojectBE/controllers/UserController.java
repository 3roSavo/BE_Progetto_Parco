package savoginEros.ParkprojectBE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.payloads.users.NewUserDTO;
import savoginEros.ParkprojectBE.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // METODI
    @GetMapping
    //@PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

}
