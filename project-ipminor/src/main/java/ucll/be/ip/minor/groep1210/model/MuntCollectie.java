package ucll.be.ip.minor.groep1210.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "munt_collectie", uniqueConstraints =
@UniqueConstraint(name = "UniqueLandAndJaar", columnNames = { "land", "jaar" })
)
public class MuntCollectie {


    @OneToMany(mappedBy = "muntCollectie", cascade = CascadeType.ALL)
    private List<Coin> coins;

    @Id
    @GeneratedValue
    private long id;
    @NotBlank(message = "titel.missing")
    @Column(unique = true, nullable = false)
    private String titel;

    @NotBlank(message = "land.missing")
    private String land;

    @Min(value = 0, message = "jaar.negative")
    @NotNull(message = "jaar.missing")
    private Long jaar;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public Long getJaar() {
        return jaar;
    }

    public void setJaar(Long jaar) {
        this.jaar = jaar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MuntCollectie muntCollectie = (MuntCollectie) o;
        return Objects.equals(titel, muntCollectie.titel) &&
                Objects.equals(land, muntCollectie.land) &&
                Objects.equals(jaar, muntCollectie.jaar);
    }
}
