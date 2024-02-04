package savoginEros.ParkprojectBE.entities;

import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "userSet")
    private Set<Hike> favoriteHikesSet;


    // COSTRUTTORE

    public User(String userName, String email, String password, Role role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
