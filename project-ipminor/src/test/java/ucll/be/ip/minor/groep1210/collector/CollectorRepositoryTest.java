package ucll.be.ip.minor.groep1210.collector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ucll.be.ip.minor.groep1210.model.Collector;
import ucll.be.ip.minor.groep1210.model.CollectorRepository;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CollectorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CollectorRepository collectorRepository;

    // Geen custom queries dus niks om te testen
    // Voorbeeld test
    @Test
    public void givenCollectorRegistered_whenexistsCollectorByNaam_thenTrue() {

        Collector doja = CollectorBuilder.aCollectorDoja().build();
        entityManager.persistAndFlush(doja);
        Collector tim = CollectorBuilder.aCollectorTim().build();
        entityManager.persistAndFlush(tim);
        Collector john = CollectorBuilder.aCollectorJohn().build();
        entityManager.persistAndFlush(john);

        Boolean found = collectorRepository.existsCollectorByNaam(doja.getNaam());

        assertTrue(found);
    }

}
