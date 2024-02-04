package savoginEros.ParkprojectBE.payloads.users;

import jakarta.validation.constraints.NotEmpty;

public record NewUserDTO(
        @NotEmpty(message = "Lo userName è obbligatorio")
        String userName,
        @NotEmpty(message = "La email è obbligatoria")
        String email,
        @NotEmpty(message = "La password è obbligatoria")
        String password) {
}
