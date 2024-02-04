package savoginEros.ParkprojectBE.payloads.login;

import jakarta.validation.constraints.NotEmpty;

public record NewLoginDTO(
        @NotEmpty(message = "Il campo email è obbligatorio")
        String email,
        @NotEmpty(message = "Il campo password è obbligatorio")
        String password) {
}
