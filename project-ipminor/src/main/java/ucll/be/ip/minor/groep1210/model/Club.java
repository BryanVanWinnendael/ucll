package ucll.be.ip.minor.groep1210.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "club", uniqueConstraints =
@UniqueConstraint(name = "UniqueEmailAndRegio", columnNames = { "email", "regio" })
)
@Getter
@Setter
@NoArgsConstructor
public class Club {

    @JsonBackReference
    @ManyToMany(cascade=CascadeType.ALL)
    private List<Collector> collectors;

    public List<Collector> getCollectors() {
        return collectors;
    }

    public void addCollector(Collector collector) {
        this.collectors.add(collector);
        collector.addClub(this);
    }

    public void removeCollector(Collector collector) {
        this.collectors.remove(collector);
        collector.removeClub(this);
    }

    @Id
    @GeneratedValue
    private long id;

    @NotBlank(message = "naam.missing")
    @Column(unique = true, nullable = false)
    private String naam;


    @NotBlank(message = "email.missing")
    @Email(message = "email.incorrect")
    private String email;

    @NotBlank(message = "regio.missing")
    private String regio;

    @NotNull(message = "maxAantalLeden.missing")
    @Max(value=100, message = "maxAantalLeden.overflow")
    @Min(value=5, message = "minAantalLeden.underflow")
    private Long maxAantalLeden;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegio() {
        return regio;
    }

    public void setRegio(String regio) {
        this.regio = regio;
    }

    public Long getMaxAantalLeden() {
        return maxAantalLeden;
    }

    public void setMaxAantalLeden(Long maxAantalLeden) {
        this.maxAantalLeden = maxAantalLeden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Club club = (Club) o;
        return Objects.equals(naam, club.naam) &&
                Objects.equals(regio, club.regio) &&
                Objects.equals(email, club.email) &&
                Objects.equals(maxAantalLeden, club.maxAantalLeden);
    }
}
