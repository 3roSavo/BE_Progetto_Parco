package savoginEros.ParkprojectBE.payloads.users;

import savoginEros.ParkprojectBE.entities.Role;

import java.util.Set;

public record UserResponseDTO(
        long id,
        String userIcon,
        String username,
        String email,
        String password,
        Role role,
        Set<Long> hikesIdSet) {
}
