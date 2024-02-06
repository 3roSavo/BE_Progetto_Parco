package savoginEros.ParkprojectBE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.BadRequestException;
import savoginEros.ParkprojectBE.payloads.login.LoginResponseDTO;
import savoginEros.ParkprojectBE.payloads.login.NewLoginDTO;
import savoginEros.ParkprojectBE.payloads.users.NewUserDTO;
import savoginEros.ParkprojectBE.payloads.users.UserResponseDTO;
import savoginEros.ParkprojectBE.services.AuthService;
import savoginEros.ParkprojectBE.services.UserService;

import java.util.HashSet;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Validated NewLoginDTO loginDTO, BindingResult validation) {

        if (validation.hasErrors()) {

            System.out.println(validation.getAllErrors());

            throw new BadRequestException("Ci sono errori nel payload :"
                    + System.lineSeparator()
                    + validation.
                    getAllErrors()
                    .stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(System.lineSeparator()))
            );

        } else {
            return new LoginResponseDTO(authService.userAuthenticate(loginDTO));
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO saveUser(@RequestBody @Validated NewUserDTO newUser, BindingResult validation) {

        if (validation.hasErrors()) {

            System.out.println(validation.getAllErrors());

            throw new BadRequestException("Ci sono errori nel payload : "
                    + validation.getAllErrors()
                    .stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(System.lineSeparator()))
            );

        }

        User user = authService.saveUser(newUser);

        return new UserResponseDTO(
                user.getId(),
                user.getUserIcon(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                new HashSet<>()
        );
    }
}
