package ucll.be.ip.minor.groep1210.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "collector")
public class Collector {


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "collectors")
    private List<Club> clubs = new ArrayList<Club>();

    public void addClub(Club club) {
        clubs.add(club);
    }

    public void removeClub(Club club) {
        clubs.remove(club);
    }

    public List<Club> getClubs() {
        return clubs;
    }

    @Id
    @GeneratedValue
    private long id;

    @Size(min = 3, message = "naam.min")
    @NotBlank(message = "naam.missing")
    @Column(unique = true, nullable = false)
    private String naam;

    @NotBlank(message = "voornaam.missing")
    private String voornaam;

    @NotBlank(message = "regio.missing")
    private String regio;

    @Min(value = 18, message = "leeftijd.min")
    @Max(value = 110, message = "leeftijd.max")
    @NotNull(message = "jaar.missing")
    private Long leeftijd;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getRegio() {
        return regio;
    }

    public void setRegio(String regio) {
        this.regio = regio;
    }

    public Long getLeeftijd() {
        return leeftijd;
    }

    public void setLeeftijd(Long leeftijd) {
        this.leeftijd = leeftijd;
    }
}
