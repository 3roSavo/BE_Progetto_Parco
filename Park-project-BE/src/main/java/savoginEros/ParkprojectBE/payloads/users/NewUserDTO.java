package savoginEros.ParkprojectBE.payloads.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NewUserDTO(
        String userIcon,
        @NotBlank(message = "Lo username è obbligatorio")
        String username,
        @NotBlank(message = "Non è consentito un indirizzo email nullo")
        @Email(message = "Il formato della email non è valido")
        String email,
        @NotBlank(message = "La password è obbligatoria")
        String password) {
}
