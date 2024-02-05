package savoginEros.ParkprojectBE.payloads.hikes;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import savoginEros.ParkprojectBE.entities.Difficulty;

import java.util.ArrayList;
import java.util.List;

public record NewHikeDTO(
        List<String> urlImagesList,
        @NotEmpty(message = "Il titolo è obbligatorio")
        String title,
        @NotEmpty(message = "La descrizione è obbligatoria")
        String description,
        @NotEmpty(message = "La durata è obbligatoria")
        String duration,
        @NotNull(message = "La lunghezza è obbligatoria")
        double length,
        @NotNull(message = "Il dislivello è obbligatorio")
        int elevationGain,
        @NotNull(message = "Il numero del percorso è obbligatorio")
        int trailNumber,
        @NotNull(message = "La difficoltà è obbligatoria")
        @Enumerated(EnumType.STRING)
        Difficulty difficulty) {
}
