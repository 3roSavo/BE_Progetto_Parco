package savoginEros.ParkprojectBE.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    private List<String> urlImagesList = new ArrayList<>();

    private String title;

    @Column(length = 1000)
    private String description;

    private String duration;

    private double length;

    private int elevationGain;

    private int trailNumber;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_hike",
    joinColumns = @JoinColumn(name = "hike_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userSet;

    public Hike(
            String title,
            String description,
            String duration,
            double length,
            int elevationGain,
            int trailNumber,
            Difficulty difficulty) {

        this.title = title;
        this.description = description;
        this.duration = duration;
        this.length = length;
        this.elevationGain = elevationGain;
        this.trailNumber = trailNumber;
        this.difficulty = difficulty;
    }
}
