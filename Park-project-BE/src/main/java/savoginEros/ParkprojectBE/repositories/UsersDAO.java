package savoginEros.ParkprojectBE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savoginEros.ParkprojectBE.entities.User;

@Repository
public interface UsersDAO extends JpaRepository<User, Long> {
}
