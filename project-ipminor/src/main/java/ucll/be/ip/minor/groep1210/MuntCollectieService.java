package ucll.be.ip.minor.groep1210;

import org.assertj.core.internal.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ucll.be.ip.minor.groep1210.model.Coin;
import ucll.be.ip.minor.groep1210.model.CoinRepository;
import ucll.be.ip.minor.groep1210.model.MuntCollectie;
import ucll.be.ip.minor.groep1210.model.MuntCollectieRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MuntCollectieService {

    @Autowired
    private MuntCollectieRepository muntCollectieRepository;

    @Autowired
    private CoinRepository coinRepository;

    public MuntCollectie addMuntCollectie(MuntCollectie muntCollectie){
        if(muntCollectieRepository.existsMuntCollectieByTitelOrJaarAndLand(muntCollectie.getTitel(),muntCollectie.getJaar(),muntCollectie.getLand())){
            throw new ServiceException("add","munt.exist");
        }
        return muntCollectieRepository.save(muntCollectie);
    }

    public ArrayList<Integer> getPages(){
        Iterable<MuntCollectie> muntCollectie = getAllMuntCollecties();

        int MuntCollectiesSize = 0;
        for (MuntCollectie i : muntCollectie) {
            MuntCollectiesSize++;
        }

        int aantalPages = (int) Math.ceil( MuntCollectiesSize * 1.0 /  5);

        ArrayList<Integer> pages = new ArrayList<>();
        for(Integer i = 1; i <= aantalPages;i++){
            pages.add(i);
        }
        return pages;
    }

    public Iterable<MuntCollectie> findAllMuntCollecties(Pageable firstPageWithTwoElements) {
        return muntCollectieRepository.findAll(firstPageWithTwoElements);
    }

    public Iterable<MuntCollectie> getAllMuntCollecties() {
        return muntCollectieRepository.findAll();
    }

    public MuntCollectie findById(long id){
        return muntCollectieRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Munt collectie not found with id: " + id));
    }

    public Iterable<MuntCollectie> findAllSortByLand(){
        return muntCollectieRepository.findAll(Sort.by("land").descending());
    }

    public Iterable<MuntCollectie> findAllSortByYear(){
        return muntCollectieRepository.findAll(Sort.by("jaar").ascending());
    }

    public void deleteMuntCollectieWithId(long id) {
        MuntCollectie muntCollectie = muntCollectieRepository.findById(id).orElseThrow(()->new ServiceException("Delete collectoin", "Collection doesn't exist"));
        if (getAllCoins(id).size() > 0) throw new ServiceException("Delete collectoin", "Collection has coins");
        muntCollectieRepository.delete(muntCollectie);
    }

    public Iterable<MuntCollectie> findAllBySearchLand(String s){
        return muntCollectieRepository.findAllByLandContainingIgnoreCase(s);
    }

    public Iterable<MuntCollectie> findAllBySearchYear(Long l){
        return muntCollectieRepository.findAllByJaar(l);
    }

    public Iterable<MuntCollectie> findAllBySearchYearIsBefore(Long l){
        return muntCollectieRepository.findAllByJaarIsBefore(l);
    }

    public void updateMuntCollectie(MuntCollectie muntCollectie){
        String titel = muntCollectie.getTitel();
        String land = muntCollectie.getLand();
        Long jaar = muntCollectie.getJaar();
        Long id = muntCollectie.getId();

        MuntCollectie muntCollectieFound = muntCollectieRepository.getById(id);
        if (getAllCoins(id).size() > 0) throw new ServiceException("Update collectoin", "Collection has coins");
        try{

            muntCollectieFound.setTitel(titel);
            muntCollectieFound.setLand(land);
            muntCollectieFound.setJaar(jaar);
            muntCollectieRepository.save(muntCollectieFound);

        }catch(Exception e){
           throw new ServiceException("Update","muntcollectie.update.error");
        }
    }

    private Coin getCoin(long coin_id)
    {
        return coinRepository.findById(coin_id).orElseThrow(()->new ServiceException("Get coin", "The coin doesn't exist"));
    }

    public void setCoinToCollection(long coin_id, long muntCollectie_id) {
        MuntCollectie muntCollectie = findById(muntCollectie_id);
        Coin coin = getCoin(coin_id);
        if (coin.getMuntCollectie() != null) throw new ServiceException("Add coin to collection", "The coin is already in the collection");
        if (coinRepository.existsCoinByJaartalAndLandAndMuntCollectie(coin.getJaartal(), coin.getLand(), muntCollectie)) throw new ServiceException("Add coin to collection", "Combination of year and country already in"); // bestaat al in collection met jaartal land
        if (!coin.getLand().equals(muntCollectie.getLand())) throw new ServiceException("Add coin to collection", "The country of the coin is not the same as the country of the collection");
        if (coin.getJaartal() > muntCollectie.getJaar()) throw new ServiceException("Add coin to collection", "The year of the coin must be smaller or equal to the collection's year");
        coin.setMuntCollectie(muntCollectie);
        coinRepository.save(coin);
    }

    public void removeCollection(long coin_id, long muntCollectie_id) {
        MuntCollectie muntCollectie = findById(muntCollectie_id);
        Coin coin = getCoin(coin_id);
        if (coin.getMuntCollectie() == null) throw new ServiceException("Remove coin from collection", "This coin doesn't have a collection");
        if (coin.getMuntCollectie().getId() != muntCollectie.getId()) throw new ServiceException("Remove coin from collection", "This coin doesn't exist in this collection");
        coin.deleteCollection();
        coinRepository.save(coin);
    }

    public List<Coin> getAllCoins(long id){
        MuntCollectie muntCollectie = muntCollectieRepository.findById(id).orElseThrow(()->new ServiceException("Get all coins from collection", "CoinCollection not found with id"));
        return coinRepository.findAllByMuntCollectie(muntCollectie);
    }
}
