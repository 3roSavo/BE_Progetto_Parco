package savoginEros.ParkprojectBE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import savoginEros.ParkprojectBE.entities.Hike;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.BadRequestException;
import savoginEros.ParkprojectBE.payloads.hikes.GpxUrlFile;
import savoginEros.ParkprojectBE.payloads.hikes.HikeResponseDTO;
import savoginEros.ParkprojectBE.payloads.hikes.HikesPictureList;
import savoginEros.ParkprojectBE.payloads.hikes.NewHikeDTO;
import savoginEros.ParkprojectBE.payloads.users.Relation_User_Hike;
import savoginEros.ParkprojectBE.services.HikeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hikes")
public class HikeController {
    
    @Autowired
    private HikeService hikeService;
    
    
    @GetMapping
    public Page<HikeResponseDTO> getAllHikes(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "title") String sort) {
        
        Page<Hike> hikeList = hikeService.getAllHikes(page, size, sort);
        
        return hikeList.map(hike -> {
            
            List<Long> usersIdList = new ArrayList<>();
            
            hike.getUserList().forEach(user -> usersIdList.add(user.getId()));
            
            return new HikeResponseDTO(hike.getId(), hike.getUrlImagesList(), hike.getTitle(), hike.getDescription(), hike.getDuration(), hike.getLength(), hike.getElevationGain(), hike.getTrailNumber(), hike.getDifficulty(), usersIdList, hike.getGpxUrlFile());
        });
    }
        /*hikeList.forEach(hike -> {
            List<Long> usersIdList = new ArrayList<>();
            hike.getUserList().forEach(user -> usersIdList.add(user.getId()));

            hikeResponseDTOList.add(new HikeResponseDTO(
                    hike.getId(),
                    hike.getUrlImagesList(),
                    hike.getTitle(),
                    hike.getDescription(),
                    hike.getDuration(),
                    hike.getLength(),
                    hike.getElevationGain(),
                    hike.getTrailNumber(),
                    hike.getDifficulty(),
                    usersIdList
            ));
        });
        return hikeResponseDTOList;*/
    
    @GetMapping("/title/{title}")
    public Page<HikeResponseDTO> getHikeByTitle(@PathVariable String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "title") String sort) {
        
        Page<Hike> hikeList = hikeService.findByTitle(title, page, size, sort);
        
        return hikeList.map(hike -> {
            
            List<Long> usersIdList = new ArrayList<>();
            
            hike.getUserList().forEach(user -> usersIdList.add(user.getId()));
            
            return new HikeResponseDTO(hike.getId(), hike.getUrlImagesList(), hike.getTitle(), hike.getDescription(), hike.getDuration(), hike.getLength(), hike.getElevationGain(), hike.getTrailNumber(), hike.getDifficulty(), usersIdList, hike.getGpxUrlFile());
        });
    }
    
    @GetMapping("/{hikeId}")
    public HikeResponseDTO getHikeById(@PathVariable long hikeId) {
        
        Hike hike = hikeService.getHikeById(hikeId);
        List<Long> usersIdList = new ArrayList<>();
        hike.getUserList().forEach(user -> usersIdList.add(user.getId()));
        
        return new HikeResponseDTO(hike.getId(), hike.getUrlImagesList(), hike.getTitle(), hike.getDescription(), hike.getDuration(), hike.getLength(), hike.getElevationGain(), hike.getTrailNumber(), hike.getDifficulty(), usersIdList, hike.getGpxUrlFile());
    }
    
    
    // Aggiunta ai preferiti ------------------
    @PutMapping("/me/addFavourite/{hikeId}")
    public Relation_User_Hike addToFavourite(@AuthenticationPrincipal User user, @PathVariable long hikeId) {
        
        return hikeService.addToFavourites(user, hikeId);
    }
    
    // Rimozione dai preferiti --------------
    @PutMapping("/me/removeFavourite/{hikeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavourite(@AuthenticationPrincipal User user, @PathVariable long hikeId) {
        hikeService.removeFavourite(user, hikeId);
    }
    
    
    // FOR ADMINISTRATORS
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public HikeResponseDTO saveHike(@RequestBody @Validated NewHikeDTO hikeDTO, BindingResult validation) {
        
        if (validation.hasErrors()) {
            
            System.out.println(validation.getAllErrors());
            
            List<String> errorMessages = validation.getAllErrors().stream().map(objectError -> {
                String fieldName = ((FieldError) objectError).getField();
                String errorMessage = objectError.getDefaultMessage();
                return fieldName + ": " + errorMessage;
            }).collect(Collectors.toList());
            
            String errorMessageString = String.join(System.lineSeparator(), errorMessages);
            
            throw new BadRequestException("Ci sono errori nel payload: " + System.lineSeparator() + errorMessageString);
        }
        
        Hike hike = hikeService.saveHike(hikeDTO);
        
        return new HikeResponseDTO(hike.getId(), hike.getUrlImagesList(), hike.getTitle(), hike.getDescription(), hike.getDuration(), hike.getLength(), hike.getElevationGain(), hike.getTrailNumber(), hike.getDifficulty(), new ArrayList<>(), hike.getGpxUrlFile());
    }
    
    @PutMapping("/{hikeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HikeResponseDTO modifyHike(@PathVariable long hikeId, @RequestBody @Validated NewHikeDTO hikeDTO, BindingResult validation) {
        
        if (validation.hasErrors()) {
            
            System.out.println(validation.getAllErrors());
            
            throw new BadRequestException("Ci sono errori nel payload : " + validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(System.lineSeparator())));
        }
        
        Hike hike = hikeService.modifyHike(hikeId, hikeDTO);
        List<Long> usersIdList = new ArrayList<>();
        hike.getUserList().forEach(user -> usersIdList.add(user.getId()));
        
        return new HikeResponseDTO(hike.getId(), hike.getUrlImagesList(), hike.getTitle(), hike.getDescription(), hike.getDuration(), hike.getLength(), hike.getElevationGain(), hike.getTrailNumber(), hike.getDifficulty(), usersIdList, hike.getGpxUrlFile());
        
    }
    
    @DeleteMapping("/{hikeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHike(@PathVariable long hikeId, @RequestBody HikesPictureList pictureList) throws Exception {
        
        hikeService.deleteHike(hikeId, pictureList);
    }

    /*@DeleteMapping("/deletePictures/{pictureId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deletePictures(@PathVariable String pictureId) throws IOException {
        hikeService.deletePicture(pictureId);
    }*/
    
    // Prova eliminazione immagini multiple, la utilizzerò sia per la PUT dell' hike sia per la DELETE dell' hike
    @DeleteMapping("/{hikeId}/deletePictures")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<String> deletePictures(@PathVariable long hikeId, @RequestBody HikesPictureList pictureList) throws Exception {
        return hikeService.deletePictures(hikeId, pictureList);
    }
    
    
    @PutMapping("/{hikeId}/uploadListPictures")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<String> uploadListPictures(@PathVariable long hikeId, @RequestParam("pictures") MultipartFile[] file) throws IOException {
        
        return hikeService.uploadListPictures(hikeId, file);
    }
    
    @PostMapping("/{hikeId}/uploadFileGPX")
    @PreAuthorize("hasAuthority('ADMIN')")
    public GpxUrlFile uploadFileGPX(@PathVariable long hikeId, @RequestParam("fileGPX") MultipartFile fileGPX) throws IOException {
        
        return new GpxUrlFile(hikeService.uploadFileGPX(hikeId, fileGPX));
    }
    
    
}
