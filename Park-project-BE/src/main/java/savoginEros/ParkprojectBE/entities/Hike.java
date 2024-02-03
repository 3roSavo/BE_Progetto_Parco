package savoginEros.ParkprojectBE.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hike {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;

    private List<String> urlImagesList;

    private String title;

    private String description;

    private String duration;

    private double length;

    private int elevationGain;

    private int trailNumber;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ManyToMany
    @JoinTable(name = "user_hike",
    joinColumns = @JoinColumn(name = "hike_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userSet;

}
