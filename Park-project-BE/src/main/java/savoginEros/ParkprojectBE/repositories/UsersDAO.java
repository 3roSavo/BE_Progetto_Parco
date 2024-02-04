package savoginEros.ParkprojectBE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savoginEros.ParkprojectBE.entities.User;

import java.util.Optional;

@Repository
public interface UsersDAO extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
