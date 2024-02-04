package savoginEros.ParkprojectBE.payloads.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record NewUserDTO(
        @NotEmpty(message = "Lo userName è obbligatorio")
        String username,
        @NotEmpty(message = "La email è obbligatoria")
        @Email(message = "Il formato della email non è valido")
        String email,
        @NotEmpty(message = "La password è obbligatoria")
        String password) {
}
