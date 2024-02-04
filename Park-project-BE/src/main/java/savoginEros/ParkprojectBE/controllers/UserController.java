package savoginEros.ParkprojectBE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.payloads.users.NewUserDTO;
import savoginEros.ParkprojectBE.payloads.users.UserModifyForAdminsDTO;
import savoginEros.ParkprojectBE.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // METODI

    /*@GetMapping("/{userId}")        // sostituita da /me
    public User getUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }*/

    // /me per get del proprio profilo
    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User user) {
        return user;
    }

    // /me per put

    // /me per delete




    // REQUESTS FOR ADMINISTRATOR
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User modifyUserById(@PathVariable long userId, @RequestBody UserModifyForAdminsDTO userDTO) {
        return userService.modifyUserById(userId, userDTO);
    }
}
