package savoginEros.ParkprojectBE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savoginEros.ParkprojectBE.entities.Hike;

@Repository
public interface HikesDAO extends JpaRepository<Hike, Long> {
}
