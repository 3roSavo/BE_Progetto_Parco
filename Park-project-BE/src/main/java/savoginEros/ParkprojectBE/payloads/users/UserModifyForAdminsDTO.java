package savoginEros.ParkprojectBE.payloads.users;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import savoginEros.ParkprojectBE.entities.Role;

public record UserModifyForAdminsDTO(
        String userIcon,
        @NotBlank(message = "Lo username è obbligatorio")
        String username,
        @NotBlank(message = "La email è obbligatoria")
        String email,
        @NotBlank(message = "La password è obbligatoria")
        String password,
        @NotNull(message = "Il ruolo è obbligatorio")
        @Enumerated(EnumType.STRING)
        Role role) {
}
