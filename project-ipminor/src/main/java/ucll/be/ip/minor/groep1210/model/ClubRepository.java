package ucll.be.ip.minor.groep1210.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    List<Club> findAllByNaamOrderByNaamDesc(String naam);

    List<Club> findAllByMaxAantalLedenOrderByMaxAantalLedenAsc(Integer maxAantalLeden);

    Boolean existsClubsByEmailAndRegioOrNaam(String email, String regio, String naam);

    List<Club> findAllByNaamContainingIgnoreCase(String name);

    List<Club> findAllByMaxAantalLeden(Long maxAantal);

    List<Club> findAllByMaxAantalLedenGreaterThanEqual(Long aantal);


}

