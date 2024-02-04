package savoginEros.ParkprojectBE.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import savoginEros.ParkprojectBE.entities.Role;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.NotFoundException;
import savoginEros.ParkprojectBE.payloads.users.NewUserDTO;
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

        User user = new User(
                newUser.userName(),
                newUser.email(),
                newUser.password(),
                Role.USER);

        return usersDAO.save(user);
    }

    public User getUserById(long id) {
        return usersDAO.findById(id).orElseThrow( () -> new NotFoundException(id));
    }

    public User findByEmail(String email) {
       return usersDAO.findByEmail(email).orElseThrow( () -> new NotFoundException("Utente con email " + email + " non trovato nel DB"));
    }
}
