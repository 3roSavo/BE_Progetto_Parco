package savoginEros.ParkprojectBE.payloads.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record NewLoginDTO(
        @NotEmpty(message = "Il campo email è obbligatorio")
        @Email(message = "Il formato della email non è valido")
        String email,
        @NotEmpty(message = "Il campo password è obbligatorio")
        String password) {
}
