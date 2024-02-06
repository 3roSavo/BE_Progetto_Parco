package savoginEros.ParkprojectBE.payloads.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import savoginEros.ParkprojectBE.urlValidation.ValidUrl;

public record NewUserDTO(
        @ValidUrl(message = "URL fornito non valido")
        String userIcon,
        @NotBlank(message = "Lo username è obbligatorio")
        @Size(min = 4, max = 30, message = "Lo username deve essere compreso tra 4 e 30 caratteri!")
        String username,
        @NotBlank(message = "Non è consentito un indirizzo email nullo")
        @Email(message = "Il formato della email non è valido")
        String email,
        @NotBlank(message = "La password è obbligatoria")
        @Size(min = 4, max = 30, message = "La password deve essere compresa tra 4 e 30 caratteri!")
        String password) {
}
