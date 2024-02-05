package savoginEros.ParkprojectBE.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import savoginEros.ParkprojectBE.entities.Role;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.BadRequestException;
import savoginEros.ParkprojectBE.exceptions.NotFoundException;
import savoginEros.ParkprojectBE.payloads.users.NewUserDTO;
import savoginEros.ParkprojectBE.payloads.users.UserModifyForAdminsDTO;
import savoginEros.ParkprojectBE.repositories.UsersDAO;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersDAO usersDAO;

    //METODI

    public List<User> getAllUsers() {
        return usersDAO.findAll();
    }

    public User saveUser(NewUserDTO newUser) {

        if (usersDAO.findByEmail(newUser.email()).isPresent()) {
            throw new BadRequestException("La email " + newUser.email() + " è già presente nel database! Cambiala per favore");
        }

        User user = new User(
                newUser.username(),
                newUser.email(),
                newUser.password(),
                Role.USER);

        if (newUser.userIcon() != null) {
            user.setUserIcon(newUser.userIcon());
        }

        return usersDAO.save(user);
    }

    public User getUserById(long id) {
        return usersDAO.findById(id).orElseThrow( () -> new NotFoundException(id));
    }

    public void deleteUser(long userId) {
        User user = getUserById(userId);
        usersDAO.delete(user);
    }

    public User modifyUserById(long userId, UserModifyForAdminsDTO userDTO) {

        if (usersDAO.findByEmail(userDTO.email()).isPresent()) {
            throw new BadRequestException("La email " + userDTO.email() + " è già presente nel DB");
        }

        User user = getUserById(userId);


        if (userDTO.userIcon() != null) {
            user.setUserIcon(userDTO.userIcon());
        }

        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setRole(userDTO.role());

        return usersDAO.save(user);
    }

    public User findByEmail(String email) {
       return usersDAO.findByEmail(email).orElseThrow( () -> new NotFoundException("Utente con email " + email + " non trovato nel DB"));
    }
}
