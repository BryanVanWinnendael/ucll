package ucll.be.ip.minor.groep1210.Coin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import ucll.be.ip.minor.groep1210.model.Coin;
import ucll.be.ip.minor.groep1210.model.CoinRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CoinRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CoinRepository coinRepository;

    @Test
    public void WithCoinRegistered_WhenfindAllByJaartalIsExecuted_AllCoinsWithGivenYearAreGiven() {
        Coin y2000 = CoinBuilder.aValidCoinTestcoin().build();
        entityManager.persistAndFlush(y2000);
        Coin y1200 = CoinBuilder.aValidCoinTestcoin2().build();
        entityManager.persistAndFlush(y1200);
        Coin y1450 = CoinBuilder.aValidCoinTestcoin3().build();
        entityManager.persistAndFlush(y1450);


        List<Coin> coins = coinRepository.findAllByJaartal(y1200.getJaartal());

        assertTrue(coins.get(0).getJaartal()== y1200.getJaartal());

    }

    @Test
    public void WithCoinRegistered_WhenfindAllByLandContainingIsExecuted_AllCoinsWithLandYearAreGiven() {
        Coin lBelgium = CoinBuilder.aValidCoinTestcoin().build();
        entityManager.persistAndFlush(lBelgium);
        Coin lNetherlands = CoinBuilder.aValidCoinTestcoin2().build();
        entityManager.persistAndFlush(lNetherlands);
        Coin lFrance = CoinBuilder.aValidCoinTestcoin3().build();
        entityManager.persistAndFlush(lFrance);


        List<Coin> coins = coinRepository.findAllByLandContaining("Belg");

        assertTrue(coins.get(0).getLand().equals(lBelgium.getLand()));

    }


}
