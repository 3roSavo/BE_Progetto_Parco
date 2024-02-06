package savoginEros.ParkprojectBE.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import savoginEros.ParkprojectBE.entities.Hike;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.NotFoundException;
import savoginEros.ParkprojectBE.payloads.hikes.NewHikeDTO;
import savoginEros.ParkprojectBE.payloads.users.Relation_User_Hike;
import savoginEros.ParkprojectBE.repositories.HikesDAO;
import savoginEros.ParkprojectBE.repositories.UsersDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HikeService {

    @Autowired
    private HikesDAO hikesDAO;
    @Autowired
    private UsersDAO usersDAO;


    // METODI

    public List<Hike> getAllHikes() {
        return hikesDAO.findAll();
    }

    public List<Hike> findByTitle(String partialTitle) {
        return hikesDAO.findByTitleContainingIgnoreCase(partialTitle);
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

        hike.getUserSet().forEach(user -> user.getFavoriteHikesSet().remove(hike));
        hikesDAO.delete(hike);
    }

    // AGGIUNTA AI PREFERITI
    public Relation_User_Hike addToFavourites(User user, long hikeId) {

        Hike hike = getHikeById(hikeId);
        hike.getUserSet().add(user);
        hikesDAO.save(hike);

        return new Relation_User_Hike(user.getId(), hike.getId());
    }

}
