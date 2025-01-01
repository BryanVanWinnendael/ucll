package ucll.be.ip.minor.groep1210.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.List;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {

        /*
        @Query( "SELECT COIN.ID, COIN.JAARTAL, COIN.LAND, COIN.MUNTEENHEID , COIN.NAAM, COIN.WAARDE, MUNT_COLLECTIE.TITEL FROM COIN LEFT OUTER JOIN MUNT_COLLECTIE ON MUNT_COLLECTIE_ID = MUNT_COLLECTIE.ID")
        List<Coin> findAllCoinsAndCollectionName();
        */

        Boolean existsCoinsByNaam(String naam);

        List<Coin> findAllByLandContaining(String s);

        List<Coin> findAllByJaartal(int jaar);

        boolean existsCoinByJaartalAndLandAndMuntCollectie (int jaartal, String land, MuntCollectie collectie);

        List<Coin> findAllByMuntCollectie (@Valid MuntCollectie muntCollectie);
}