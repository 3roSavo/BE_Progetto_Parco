package savoginEros.ParkprojectBE.payloads.users;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import savoginEros.ParkprojectBE.entities.Role;
import savoginEros.ParkprojectBE.urlValidation.ValidUrl;

public record UserModifyForAdminsDTO(
        @ValidUrl(message = "URL fornito non valido")
        String userIcon,
        @NotBlank(message = "Lo username è obbligatorio")
        @Size(min = 4, max = 30, message = "Lo username deve essere compreso tra 4 e 30 caratteri!")
        String username,
        @NotBlank(message = "La email è obbligatoria")
        String email,
        @NotBlank(message = "La password è obbligatoria")
        @Size(min = 4, max = 30, message = "La password deve essere compresa tra 4 e 30 caratteri!")
        String password,
        @NotNull(message = "Il ruolo è obbligatorio")
        @Enumerated(EnumType.STRING)
        Role role) {
}
