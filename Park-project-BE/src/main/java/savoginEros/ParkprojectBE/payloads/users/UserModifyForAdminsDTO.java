package savoginEros.ParkprojectBE.payloads.users;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import savoginEros.ParkprojectBE.entities.Role;

public record UserModifyForAdminsDTO(
        String userIcon,
        String username,
        String email,
        String password,
        @Enumerated(EnumType.STRING)
        Role role) {
}
