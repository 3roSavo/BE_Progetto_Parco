package savoginEros.ParkprojectBE.payloads.hikes;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import savoginEros.ParkprojectBE.entities.Difficulty;
import savoginEros.ParkprojectBE.urlValidation.ValidUrl;

import java.util.ArrayList;
import java.util.List;

public record NewHikeDTO(
        List<String> urlImagesList,
        @NotBlank(message = "Il titolo è obbligatorio")
        @Size(min = 5, max = 30, message = "Il titolo deve essere compreso tra 5 e 30 caratteri")
        String title,
        @NotBlank(message = "La descrizione è obbligatoria")
        @Size(min = 12, max = 1000, message = "La descrizione deve essere compreso tra 12 e 1000 caratteri")
        String description,
        @NotBlank(message = "La durata è obbligatoria")
        @Size(min = 3, max = 10, message = "La durata deve essere compreso tra 3 e 10 caratteri")
        String duration,
        @NotNull(message = "La lunghezza è obbligatoria")
        @Max(50)
        double length,
        @Min(0)
        @Max(5000)
        @NotNull(message = "Il dislivello è obbligatorio")
        int elevationGain,
        @NotNull(message = "Il numero del percorso è obbligatorio")
        @Min(1)
        @Max(10000)
        int trailNumber,
        @NotNull(message = "La difficoltà è obbligatoria")
        @Enumerated(EnumType.STRING)
        Difficulty difficulty) {


        @AssertTrue(message = "La lunghezza del percorso deve essere diversa da 0")
        private boolean isLengthNumberValid() {
                return length != 0;
        }

}
