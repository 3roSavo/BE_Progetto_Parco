package savoginEros.ParkprojectBE.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.NotFoundException;
import savoginEros.ParkprojectBE.repositories.HikesDAO;
import savoginEros.ParkprojectBE.repositories.UsersDAO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private HikesDAO hikesDAO;
    @Autowired
    private Cloudinary cloudinary;

    //METODI CRUD (POST e PUT migrate in authService causa loop dependency injection)

    public List<User> getAllUsers() {
        return usersDAO.findAll();
    }

    public User getUserById(long id) {
        return usersDAO.findById(id).orElseThrow( () -> new NotFoundException("Utente", id));
    }

    public void deleteUser(long userId) {

        User user = getUserById(userId);

        user.getFavoriteHikesList().forEach(hike -> hike.getUserList().remove(user));

        //List<Hike> favoriteHikesCopy = new ArrayList<>(user.getFavoriteHikesList());
        //favoriteHikesCopy.forEach(hike -> hike.getUserList().remove(user));

        // VARIANTE con ciclo FOR
        /*for (Hike hike : favoriteHikesCopy) {
            hike.getUserSet().remove(user);
        }*/
        usersDAO.delete(user);
    }

    public User findByEmail(String email) {
       return usersDAO.findByEmail(email).orElseThrow( () -> new NotFoundException("Utente con email " + email + " non trovato nel DB"));
    }

    public String uploadPicture(User user, MultipartFile file) throws IOException {
        Map<String, String> options = new HashMap<>();

        options.put("folder", "Progetto_Parco/Icone_Utenti");

        String url = (String) cloudinary.uploader().upload(file.getBytes(), options).get("url");
        user.setUserIcon(url);
        usersDAO.save(user);

        return url;
    }
}
