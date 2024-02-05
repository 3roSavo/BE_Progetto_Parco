package savoginEros.ParkprojectBE.payloads.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NewLoginDTO(
        @NotBlank(message = "Il campo email è obbligatorio")
        @Email(message = "Il formato della email non è valido")
        String email,
        @NotBlank(message = "Il campo password è obbligatorio")
        String password) {
}
