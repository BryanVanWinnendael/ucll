package ucll.be.ip.minor.groep1210.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "coin")
@Getter
@Setter
@NoArgsConstructor
public class Coin {

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private MuntCollectie muntCollectie;


    public String getMuntCollectieTitel(){
        if (muntCollectie != null){
            return muntCollectie.getTitel();
        }
        return "Heeft geen muntcollectie";
    }

    @JsonIgnore
    public MuntCollectie getMuntCollectie() {
        return muntCollectie;
    }

    @JsonIgnore
    public void setMuntCollectie(@Valid MuntCollectie muntCollectie) {
        this.muntCollectie = muntCollectie;
    }

    public void deleteCollection(){
        this.muntCollectie = null;
    }

    @Id
    @GeneratedValue
    private long id;

    @NotBlank(message = "naam.missing")
    @Column(unique = true, nullable = false)
    private String naam;

    @NotBlank(message = "land.missing")
    private String land;

    @NotBlank(message = "munteenheid.missing")
    private String munteenheid;

    @Min(value = 0, message = "waarde.negative")
    @NotNull(message = "waarde.missing")
    private Double waarde;

    @Min(value = 0, message = "jaartal.negative")
    @Max(value = 2022, message = "jaartal.max")
    @NotNull(message = "jaartal.missing")
    private int jaartal;

}
