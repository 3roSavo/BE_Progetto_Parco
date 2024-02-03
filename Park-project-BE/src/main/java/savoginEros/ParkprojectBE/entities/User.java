package savoginEros.ParkprojectBE.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;

    private String userIcon; // non definitivo

    private String userName;

    private String email;

    private String password;

    @ManyToMany(mappedBy = "userSet")
    private Set<Hike> favoriteHikesSet;
}
