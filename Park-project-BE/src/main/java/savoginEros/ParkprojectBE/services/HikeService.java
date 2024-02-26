package savoginEros.ParkprojectBE.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import savoginEros.ParkprojectBE.entities.Hike;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.NotFoundException;
import savoginEros.ParkprojectBE.payloads.hikes.HikesPictureList;
import savoginEros.ParkprojectBE.payloads.hikes.NewHikeDTO;
import savoginEros.ParkprojectBE.payloads.users.Relation_User_Hike;
import savoginEros.ParkprojectBE.repositories.HikesDAO;
import savoginEros.ParkprojectBE.repositories.UsersDAO;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HikeService {

    @Autowired
    private HikesDAO hikesDAO;
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private Cloudinary cloudinary;


    // METODI

    public Page<Hike> getAllHikes(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Hike> hikePage = hikesDAO.findAll(pageable);

        return hikePage;
    }

    public Page<Hike> findByTitle(String partialTitle, int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        return hikesDAO.findByTitleContainingIgnoreCase(partialTitle, pageable);
    }

    public Hike getHikeById(long id) {
        return hikesDAO.findById(id).orElseThrow( () -> new NotFoundException("Escursione", id));
    }


    public Hike saveHike(NewHikeDTO hikeDTO) {
        Hike hike = new Hike(
                hikeDTO.title(),
                hikeDTO.description(),
                hikeDTO.duration(),
                hikeDTO.length(),
                hikeDTO.elevationGain(),
                hikeDTO.trailNumber(),
                hikeDTO.difficulty());

        if (hikeDTO.urlImagesList() != null) {
            hikeDTO.urlImagesList().forEach(urlString -> hike.getUrlImagesList().add(urlString));
        }
        return hikesDAO.save(hike);
    }

    public Hike modifyHike(long id, NewHikeDTO hikeDTO) {
        Hike hike = getHikeById(id);

        hike.setTitle(hikeDTO.title());
        hike.setDescription(hikeDTO.description());
        hike.setDuration(hikeDTO.duration());
        hike.setLength(hikeDTO.length());
        hike.setElevationGain(hikeDTO.elevationGain());
        hike.setTrailNumber(hikeDTO.trailNumber());
        hike.setDifficulty(hikeDTO.difficulty());

        // MEMO
        // Per aggiungere o rimuovere foto dalla lista foto o mi faccio un end-point dedicato
        // oppure a ogni sua modifica elimino la lista precedente con la nuova lista di foto.
        // Per ora nel caso ci siano foto nel payload mi limito ad aggiungerle alla lista e basta
        if (hikeDTO.urlImagesList() != null) {
            hikeDTO.urlImagesList().forEach(urlImage -> hike.getUrlImagesList().add(urlImage));
        }
        return hikesDAO.save(hike);
    }

    public void deleteHike(long hikeId) {

        Hike hike = getHikeById(hikeId);

        //Set<User> userSet = new HashSet<>(hike.getUserSet());
        //userSet.forEach(user -> user.getFavoriteHikesSet().remove(hike));

        hike.getUserList().forEach(user -> user.getFavoriteHikesList().remove(hike));
        hikesDAO.delete(hike);
    }

    // AGGIUNTA AI PREFERITI
    public Relation_User_Hike addToFavourites(User user, long hikeId) {

        Hike hike = getHikeById(hikeId);

        if (!user.getFavoriteHikesList().contains(hike)) {
        hike.getUserList().add(user);
        hikesDAO.save(hike);
        }
        return new Relation_User_Hike(user.getId(), hike.getId());
    }
    // RIMOZIONE DAI PREFERITI --------------
    public void removeFavourite(User user, long hikeId) {

        Hike hike = getHikeById(hikeId);

        user.getFavoriteHikesList().remove(hike);
        hike.getUserList().remove(user);

        usersDAO.save(user);
    }

    public static String extractPictureId(String input) {

        String regex = ".*/([^/]+)\\..*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    public List<String> uploadListPictures(long hikeId, MultipartFile[] file) throws IOException {

        Hike hike = getHikeById(hikeId);

        Map<String, String> options = new HashMap<>();
        options.put("folder", "Progetto_Parco/Galleria_Foto_Escursioni");

        for (int i = 0; i < file.length; i++) {

        String url = (String) cloudinary.uploader().upload(file[i].getBytes(), options).get("url");
        hike.getUrlImagesList().add(url);
        }

        hikesDAO.save(hike);

        return hike.getUrlImagesList();

    }


    public void deletePictures(long hikeId, HikesPictureList pictureList) throws Exception {

        List<String> imagesList = pictureList.hikesPictureList().stream().filter(
                picture -> !picture.equals("https://res.cloudinary.com/diklzegyw/image/upload/v1708900889/Progetto_Parco/Galleria_Foto_Escursioni/placeholder_rsuiuy.webp")).toList();


        if (!imagesList.isEmpty()) {

            Hike hike = getHikeById(hikeId);

            Map<String, String> options = new HashMap<>();
            options.put("folder", "Progetto_Parco/Galleria_Foto_Escursioni");

            List<String> pictureIds = imagesList
                .stream()
                .map(string -> "Progetto_Parco/Galleria_Foto_Escursioni/" + extractPictureId(string))
                .toList();

            System.out.println(pictureIds);

            cloudinary.api().deleteResources(pictureIds, options);

            hike.getUrlImagesList().removeAll(imagesList);

            // Attenzione necessiti di un iteratore qui sotto
            /*hike.getUrlImagesList().forEach(string -> {
                hike.getUrlImagesList().remove(string);
            });*/

            //cloudinary.uploader().destroy("Progetto_Parco/Icone_Utenti/" + pictureId, ObjectUtils.emptyMap());

            hikesDAO.save(hike);
        }

    }

}
