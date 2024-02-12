package savoginEros.ParkprojectBE.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savoginEros.ParkprojectBE.entities.Hike;

import java.util.List;

@Repository
public interface HikesDAO extends JpaRepository<Hike, Long> {

    Page<Hike> findByTitleContainingIgnoreCase(String partialHikeTitle, Pageable pageable);
}
