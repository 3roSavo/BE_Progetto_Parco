package savoginEros.ParkprojectBE.payloads.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import savoginEros.ParkprojectBE.urlValidation.ValidUrl;

public record NewUserDTO(
        @ValidUrl(message = "URL fornito non valido")
        String userIcon,
        @NotBlank(message = "Lo username è obbligatorio")
        String username,
        @NotBlank(message = "Non è consentito un indirizzo email nullo")
        @Email(message = "Il formato della email non è valido")
        String email,
        @NotBlank(message = "La password è obbligatoria")
        String password) {
}
