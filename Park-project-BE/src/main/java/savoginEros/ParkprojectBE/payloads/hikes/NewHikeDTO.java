package savoginEros.ParkprojectBE.payloads.hikes;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import savoginEros.ParkprojectBE.entities.Difficulty;

import java.util.ArrayList;
import java.util.List;

public record NewHikeDTO(
        List<String> urlImagesList,
        @NotBlank(message = "Il titolo è obbligatorio")
        String title,
        @NotBlank(message = "La descrizione è obbligatoria")
        String description,
        @NotBlank(message = "La durata è obbligatoria")
        String duration,
        @NotNull(message = "La lunghezza è obbligatoria")
        double length,
        @NotNull(message = "Il dislivello è obbligatorio")
        int elevationGain,
        @NotNull(message = "Il numero del percorso è obbligatorio")
        int trailNumber,
        @NotBlank(message = "La difficoltà è obbligatoria")
        @Enumerated(EnumType.STRING)
        Difficulty difficulty) {

        @AssertTrue(message = "Il numero del percorso deve essere diverso da zero")
        private boolean isTrailNumberValid() {
                return trailNumber != 0;
        }
        @AssertTrue(message = "La lunghezza del percorso deve essere diversa da 0")
        private boolean isLengthNumberValid() {
                return length != 0;
        }

}
