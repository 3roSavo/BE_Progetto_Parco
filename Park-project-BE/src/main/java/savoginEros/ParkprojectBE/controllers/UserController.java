package savoginEros.ParkprojectBE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.BadRequestException;
import savoginEros.ParkprojectBE.payloads.users.NewUserDTO;
import savoginEros.ParkprojectBE.payloads.users.UserIconUrl;
import savoginEros.ParkprojectBE.payloads.users.UserModifyForAdminsDTO;
import savoginEros.ParkprojectBE.payloads.users.UserResponseDTO;
import savoginEros.ParkprojectBE.services.AuthService;
import savoginEros.ParkprojectBE.services.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    // METODI CRUD

    @GetMapping("/{userId}")
    public UserResponseDTO getUserById(@PathVariable long userId) {

        User user = userService.getUserById(userId);

        List<Long> hikesIdList = new ArrayList<>();
        user.getFavoriteHikesList().forEach(hike -> hikesIdList.add(hike.getId()));

        return new UserResponseDTO(
                user.getId(),
                user.getUserIcon(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                hikesIdList
        );

    }

    // /me per get del proprio profilo
    @GetMapping("/me")
    public UserResponseDTO getMeProfile(@AuthenticationPrincipal User user) {

        List<Long> hikesIdList = new ArrayList<>();
        user.getFavoriteHikesList().forEach(hike -> hikesIdList.add(hike.getId()));

        return new UserResponseDTO(
                user.getId(),
                user.getUserIcon(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                hikesIdList
        );
    }

    // /me per put
    @PutMapping("/me")
    public UserResponseDTO modifyMeProfile(@AuthenticationPrincipal User userFound, @RequestBody @Validated NewUserDTO userDTO, BindingResult validation) {

        if (validation.hasErrors()) {

            System.out.println(validation.getAllErrors());

            throw new BadRequestException("Ci sono errori nel payload : "
                    + validation.getAllErrors()
                    .stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(System.lineSeparator()))
            );

        }

        User user = authService.modifyUserByIdForUsers(userFound, userDTO);

        List<Long> hikesIdList = new ArrayList<>();
        user.getFavoriteHikesList().forEach(hike -> hikesIdList.add(hike.getId()));

        return new UserResponseDTO(
                user.getId(),
                user.getUserIcon(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                hikesIdList
        );
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
    public List<UserResponseDTO> getAllUsers() {

        List<User> userList = userService.getAllUsers();

        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();


        userList.forEach(user -> {

            List<Long> hikesIdList = new ArrayList<>();
            user.getFavoriteHikesList().forEach(hike -> hikesIdList.add(hike.getId()));

            userResponseDTOList.add(new UserResponseDTO(
                    user.getId(),
                    user.getUserIcon(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole(),
                    hikesIdList
            ));
        });
        return userResponseDTOList;
    }
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long userId) {

        userService.deleteUser(userId);
    }
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDTO modifyUserById(@PathVariable long userId, @RequestBody @Validated UserModifyForAdminsDTO userDTO, BindingResult validation) {

        if (validation.hasErrors()) {

            System.out.println(validation.getAllErrors());

            throw new BadRequestException("Ci sono errori nel payload : "
                    + validation.getAllErrors()
                    .stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(System.lineSeparator()))
            );
        }

        User user = authService.modifyUserByIdForAdmins(userId, userDTO);

        List<Long> hikesIdList = new ArrayList<>();
        user.getFavoriteHikesList().forEach(hike -> hikesIdList.add(hike.getId()));

        return new UserResponseDTO(
                user.getId(),
                user.getUserIcon(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                hikesIdList
        );
    }

    @PutMapping("/me/uploadIcon")
    public UserIconUrl uploadIcon(@AuthenticationPrincipal User user, @RequestParam("icon") MultipartFile file) throws IOException {

        return new UserIconUrl(userService.uploadPicture(user, file));
    }

}
