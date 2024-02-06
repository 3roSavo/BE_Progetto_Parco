package savoginEros.ParkprojectBE.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import savoginEros.ParkprojectBE.entities.Hike;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.NotFoundException;
import savoginEros.ParkprojectBE.payloads.hikes.HikeResponseDTO;
import savoginEros.ParkprojectBE.payloads.hikes.NewHikeDTO;
import savoginEros.ParkprojectBE.payloads.users.Relation_User_Hike;
import savoginEros.ParkprojectBE.repositories.HikesDAO;

import java.util.ArrayList;
import java.util.List;

@Service
public class HikeService {

    @Autowired
    private HikesDAO hikesDAO;


    // METODI

    public List<Hike> getAllHikes() {
        return hikesDAO.findAll();
    }

    public List<Hike> findByTitle(String partialTitle) {
        return hikesDAO.findByTitleContainingIgnoreCase(partialTitle);
    }

    public Hike getHikeById(long id) {
        return hikesDAO.findById(id).orElseThrow( () -> new NotFoundException(id));
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

        if (hikeDTO.urlImagesList() != null) {
            hikeDTO.urlImagesList().forEach(urlImage -> hike.getUrlImagesList().add(urlImage));
        }

        return hikesDAO.save(hike);
    }

    public void deleteHike(long hikeId) {
        Hike hike = getHikeById(hikeId);
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
