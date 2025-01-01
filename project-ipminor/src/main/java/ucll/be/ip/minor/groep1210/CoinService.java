package ucll.be.ip.minor.groep1210;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ucll.be.ip.minor.groep1210.model.*;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoinService {

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private MuntCollectieRepository muntCollectieRepository;

    public Coin addCoin(Coin coin){
        if(coinRepository.existsCoinsByNaam(coin.getNaam())){
            throw new ServiceException("add","coin.exists");
        }

        return coinRepository.save(coin);
    }

    public Iterable<Coin> getAllCoins(){
        return coinRepository.findAll();
    }

    public void updateCoin(Coin coin){
        String naam = coin.getNaam();
        String land = coin.getLand();
        String munteenheid = coin.getMunteenheid();
        Double waarde = coin.getWaarde();
        int year = coin.getJaartal();
        long id = coin.getId();

        try{
            Coin CoinFound = coinRepository.getById(id);
            CoinFound.setNaam(naam);
            CoinFound.setLand(land);
            CoinFound.setMunteenheid(munteenheid);
            CoinFound.setWaarde(waarde);
            CoinFound.setJaartal(year);

            coinRepository.save(CoinFound);

        }catch(Exception e){
            throw new ServiceException("update","coin.update.error");
        }
    }

    public void deleteCoinWithId(long id) {
        Coin coin = coinRepository.getById(id);
        if (coin.getMuntCollectie() != null) throw new ServiceException("Delete coin","Coin is nog in collection");
        coinRepository.delete(coinRepository.findById(id).orElseThrow(()->new ServiceException("delete", "coin.missing.id")));
    }

    public Iterable<Coin> getAllCoinsWithGivenYear(int year){
        return coinRepository.findAllByJaartal(year);
    }

    public Iterable<Coin> getAllCoinsWithLandContains(String s){
        return coinRepository.findAllByLandContaining(s);
    }





}
