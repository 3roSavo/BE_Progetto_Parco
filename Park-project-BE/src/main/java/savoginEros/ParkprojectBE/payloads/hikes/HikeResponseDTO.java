package savoginEros.ParkprojectBE.payloads.hikes;

import savoginEros.ParkprojectBE.entities.Difficulty;

import java.util.List;
import java.util.Set;

public record HikeResponseDTO(
        long id,
        List<String> urlImagesList,
        String title,
        String description,
        String duration,
        double length,
        int elevationGain,
        int trailNumber,
        Difficulty difficulty,
        Set<Long> usersIdSet
) {
}
