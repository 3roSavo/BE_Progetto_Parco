package savoginEros.ParkprojectBE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.BadRequestException;
import savoginEros.ParkprojectBE.payloads.users.UserModifyForAdminsDTO;
import savoginEros.ParkprojectBE.payloads.users.UserModifyForUsersDTO;
import savoginEros.ParkprojectBE.repositories.UsersDAO;
import savoginEros.ParkprojectBE.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UsersDAO usersDAO;

    // METODI CRUD

    /*@GetMapping("/{userId}")        // sostituita da /me
    public User getUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }*/

    // /me per get del proprio profilo
    @GetMapping("/me")
    public User getMeProfile(@AuthenticationPrincipal User user) {
        return user;
    }

    // /me per put
    @PutMapping("/me")
    public User modifyMeProfile(@AuthenticationPrincipal User user, @RequestBody UserModifyForUsersDTO userDTO) {

        if (usersDAO.findByEmail(userDTO.email()).isPresent()) {
            throw new BadRequestException("La email " + userDTO.email() + " è già presente nel DB");
        }

        user.setUserIcon(userDTO.userIcon());
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());

        return usersDAO.save(user);
    }

    // /me per delete
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user.getId());
    }



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
