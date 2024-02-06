package savoginEros.ParkprojectBE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import savoginEros.ParkprojectBE.entities.Hike;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.BadRequestException;
import savoginEros.ParkprojectBE.payloads.hikes.NewHikeDTO;
import savoginEros.ParkprojectBE.payloads.users.Relation_User_Hike;
import savoginEros.ParkprojectBE.services.HikeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hikes")
public class HikeController {

    @Autowired
    private HikeService hikeService;


    @GetMapping
    public List<Hike> getAllHikes() {
        return hikeService.getAllHikes();
    }

    @GetMapping("/{hikeId}")
    public Hike getHikeById(@PathVariable long hikeId) {
        return hikeService.getHikeById(hikeId);
    }

    @GetMapping("/title/{title}")
    public List<Hike> getHikeByTitle(@PathVariable String title) {
        System.out.println("Titolo ricevuto: " + title);
        return hikeService.findByTitle(title);
    }

    // Aggiunta ai preferiti ------------------

    @PutMapping("/me/addFavourites/{hikeId}")
    public Relation_User_Hike addToFavourites(@AuthenticationPrincipal User user, @PathVariable long hikeId) {

        return hikeService.addToFavourites(user, hikeId);
    }
    // Aggiunta ai preferiti ------------------



    // FOR ADMINISTRATORS
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Hike saveHike(@RequestBody @Validated NewHikeDTO hikeDTO, BindingResult validation) {

        if (validation.hasErrors()) {

            System.out.println(validation.getAllErrors());

            throw new BadRequestException("Ci sono errori nel payload : "
                    + validation.getAllErrors()
                    .stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(System.lineSeparator()))
            );
        } else {
        return hikeService.saveHike(hikeDTO);
        }
    }

    @PutMapping("/{hikeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Hike modifyHike(@PathVariable long hikeId, @RequestBody @Validated NewHikeDTO hikeDTO, BindingResult validation) {

        if (validation.hasErrors()) {

            System.out.println(validation.getAllErrors());

            throw new BadRequestException("Ci sono errori nel payload : "
                    + validation.getAllErrors()
                    .stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(System.lineSeparator()))
            );
        } else {
            return hikeService.modifyHike(hikeId, hikeDTO);
        }
    }

    @DeleteMapping("/{hikeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHike(@PathVariable long hikeId) {
        hikeService.deleteHike(hikeId);
    }





}
