package ucll.be.ip.minor.groep1210.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CollectorRepository extends JpaRepository<Collector, Long> {

    Boolean existsCollectorByNaam(String naam);

    List<Collector> findAllByRegio(String regio);

    List<Collector> findAllByNaamAndVoornaam(String naam, String voorNaam);

}