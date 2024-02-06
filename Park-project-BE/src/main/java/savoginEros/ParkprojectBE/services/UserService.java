package savoginEros.ParkprojectBE.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import savoginEros.ParkprojectBE.entities.Hike;
import savoginEros.ParkprojectBE.entities.Role;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.BadRequestException;
import savoginEros.ParkprojectBE.exceptions.NotFoundException;
import savoginEros.ParkprojectBE.payloads.users.NewUserDTO;
import savoginEros.ParkprojectBE.payloads.users.UserModifyForAdminsDTO;
import savoginEros.ParkprojectBE.repositories.HikesDAO;
import savoginEros.ParkprojectBE.repositories.UsersDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private HikesDAO hikesDAO;

    //METODI

    public List<User> getAllUsers() {
        return usersDAO.findAll();
    }

    public User getUserById(long id) {
        return usersDAO.findById(id).orElseThrow( () -> new NotFoundException("Utente", id));
    }

    public void deleteUser(long userId) {
        User user = getUserById(userId);


        Set<Hike> favoriteHikesCopy = new HashSet<>(user.getFavoriteHikesSet());

        favoriteHikesCopy.forEach(hike -> hike.getUserSet().remove(user));

        // VARIANTE con ciclo FOR
        /*for (Hike hike : favoriteHikesCopy) {
            hike.getUserSet().remove(user);
        }*/
        usersDAO.delete(user);
    }

    public User modifyUserByIdForAdmins(long userId, UserModifyForAdminsDTO userDTO) {

        User user = getUserById(userId);

        if (usersDAO.findByEmail(userDTO.email()).isPresent() && !userDTO.email().equals(user.getEmail())) {
            // se passo una email che è presente nel db e che non corrisponde alla mia: exception
            throw new BadRequestException("La email " + userDTO.email() + " è già presente nel DB");
        }

        if (userDTO.userIcon() != null) {
            user.setUserIcon(userDTO.userIcon());
        }

        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
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
        user.setPassword(userDTO.password());

        return usersDAO.save(user);
    }

    public User findByEmail(String email) {
       return usersDAO.findByEmail(email).orElseThrow( () -> new NotFoundException("Utente con email " + email + " non trovato nel DB"));
    }
}
