package savoginEros.ParkprojectBE.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import savoginEros.ParkprojectBE.entities.Role;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.BadRequestException;
import savoginEros.ParkprojectBE.exceptions.UnauthorizedException;
import savoginEros.ParkprojectBE.payloads.login.NewLoginDTO;
import savoginEros.ParkprojectBE.payloads.users.NewUserDTO;
import savoginEros.ParkprojectBE.payloads.users.UserModifyForAdminsDTO;
import savoginEros.ParkprojectBE.repositories.UsersDAO;
import savoginEros.ParkprojectBE.security.JWTTools;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsersDAO usersDAO;

    public String userAuthenticate(NewLoginDTO loginDTO) {

        // 1 Controllare presenza mail nel DB
        // 2 Verificare correttezza password fornita
        // 3 creiamo e ritorniamo il token

        User user = userService.findByEmail(loginDTO.email());

        if (passwordEncoder.matches(loginDTO.password(), user.getPassword())) {

            return jwtTools.createToken(user);

        } else {

            throw new UnauthorizedException("Password errata");
        }
    }

    public User saveUser(NewUserDTO newUser) {

        if (usersDAO.findByEmail(newUser.email()).isPresent()) {
            throw new BadRequestException("La email " + newUser.email() + " è già presente nel database! Cambiala per favore");
        }

        User user = new User(
                newUser.username(),
                newUser.email(),
                passwordEncoder.encode(newUser.password()),
                Role.USER);

        if (newUser.userIcon() != null) {
            user.setUserIcon(newUser.userIcon());
        }

        return usersDAO.save(user);
    }

    public User modifyUserByIdForAdmins(long userId, UserModifyForAdminsDTO userDTO) {

        User user = userService.getUserById(userId);

        if (usersDAO.findByEmail(userDTO.email()).isPresent() && !userDTO.email().equals(user.getEmail())) {
            // se passo una email che è presente nel db e che non corrisponde alla mia: exception
            throw new BadRequestException("La email " + userDTO.email() + " è già presente nel DB");
        }

        if (userDTO.userIcon() != null) {
            user.setUserIcon(userDTO.userIcon());
        }

        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        //user.setPassword(userDTO.password());
        user.setRole(userDTO.role());

        return usersDAO.save(user);
    }

    public User modifyUserByIdForUsers(User user, NewUserDTO userDTO) {

        if (usersDAO.findByEmail(userDTO.email()).isPresent() && !userDTO.email().equals(user.getEmail())) {
            // se passo una email che è presente nel db e che non corrisponde alla mia: exception
            throw new BadRequestException("La email " + userDTO.email() + " è già presente nel DB");
        }

        if (userDTO.userIcon() != null) {
            user.setUserIcon(userDTO.userIcon());
        }

        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        //user.setPassword(userDTO.password());

        return usersDAO.save(user);
    }
}
