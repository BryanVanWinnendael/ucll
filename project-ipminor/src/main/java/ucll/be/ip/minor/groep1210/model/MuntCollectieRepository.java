package ucll.be.ip.minor.groep1210.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MuntCollectieRepository extends JpaRepository<MuntCollectie, Long> {

    List<MuntCollectie> findAllByLandContainingIgnoreCase(String land);

    List<MuntCollectie> findAllByJaar(Long jaar);

    List<MuntCollectie> findAllByJaarIsBefore(Long jaar);

    Boolean existsMuntCollectieByTitelOrJaarAndLand(String titel,Long jaar,String land);
}

