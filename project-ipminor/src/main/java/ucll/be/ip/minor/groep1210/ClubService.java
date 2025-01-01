package ucll.be.ip.minor.groep1210;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ucll.be.ip.minor.groep1210.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService {
    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private CollectorRepository collectorRepository;

    public Club addClub(Club club){
        if(clubRepository.existsClubsByEmailAndRegioOrNaam(club.getEmail(),club.getRegio(),club.getNaam())){
            throw new ServiceException("add","club.exist");
        }
        return clubRepository.save(club);
    }

    public ArrayList<Integer> getPages(){
        Iterable<Club> clubs = getAllClubs();

        int ClubSize = 0;
        for (Club i : clubs) {
            ClubSize++;
        }

        int aantalPages = (int) Math.ceil( ClubSize * 1.0 /  5);

        ArrayList<Integer> pages = new ArrayList<>();
        for(Integer i = 1; i <= aantalPages;i++){
            pages.add(i);
        }
        return pages;
    }

    public Iterable<Club> findAllClubs(Pageable firstPageWithTwoElements) {
        return clubRepository.findAll(firstPageWithTwoElements);
    }

    public Iterable<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public Club findClubById(long id){
        return clubRepository.findById(id).orElseThrow(()-> new ServiceException("Get club","Club doesn't exist"));
    }

    public Iterable<Club> findAllClubsSortByName(){
        return clubRepository.findAll(Sort.by("naam").descending());
    }

    public Iterable<Club> findAllClubsSortByMaxAantalLeden(){
        return clubRepository.findAll(Sort.by("maxAantalLeden").ascending());
    }

    public void deleteClubWithId(long id) {
        Club club = clubRepository.findById(id).orElseThrow(()->new ServiceException("Delete club", "Club doesn't exist"));
        if (club.getCollectors().size() > 0) throw new ServiceException("Delete club", "Club contains collectors");
        clubRepository.delete(club);
    }

    public Iterable<Club> findAllBySearcName(String name){
        return clubRepository.findAllByNaamContainingIgnoreCase(name);
    }

    public Iterable<Club> findAllBySearchMaxAantal(Long aantal){
        return clubRepository.findAllByMaxAantalLeden(aantal);
    }

    public Iterable<Club> findAllBySearchMaxAantalEnHoger(Long aantal){
        return clubRepository.findAllByMaxAantalLedenGreaterThanEqual(aantal);
    }

    public void updateClub(Club club){
        String naam = club.getNaam();
        String email = club.getEmail();
        String regio = club.getRegio();
        Long maxAantalLeden = club.getMaxAantalLeden();

        Long id = club.getId();
        Club clubFound = clubRepository.findById(id).orElseThrow(()->new ServiceException("Update club", "Club doesn't exist"));
        if (clubFound.getCollectors().size() > maxAantalLeden) throw new ServiceException("Update club", "Too many collectors");
        try
        {
            clubFound.setNaam(naam);
            clubFound.setEmail(email);
            clubFound.setRegio(regio);
            clubFound.setMaxAantalLeden(maxAantalLeden);
            clubRepository.save(clubFound);
        }
        catch(Exception e)
        {
            throw new ServiceException("update", "Error updating club");
        }
    }

    public Iterable<Collector> getAllCollectors(long id) {
        Club club = clubRepository.findById(id).orElseThrow(()-> new ServiceException("Get collectors from club","Club doesn't exist"));
        return club.getCollectors();
    }

    public void addCollector(long club_id, long collector_id) {
        Club club = clubRepository.findById(club_id).orElseThrow(()-> new ServiceException("Add collector to club","Club doesn't exist"));
        Collector collector = collectorRepository.findById(collector_id).orElseThrow(()->new ServiceException("Add collector to club","Collector doesn't exist"));
        if (!club.getRegio().equals(collector.getRegio())) throw new ServiceException("Add collector to club","Region must match between Collector and Club");
        if (club.getCollectors().contains(collector)) throw new ServiceException("Add collector to club","Collector already added to club");
        if (club.getCollectors().size() >= club.getMaxAantalLeden()) throw new ServiceException("Add collector to club","Club is full");
        collector.addClub(club);
        club.addCollector(collector);
        clubRepository.save(club);
        collectorRepository.save(collector);
    }

    public void removeCollector(long club_id, long collector_id) {
        Collector collector = collectorRepository.findById(collector_id).orElseThrow(()->new ServiceException("Remove collector from club","Collector doesn't exist"));
        Club club = this.clubRepository.findById(club_id).orElseThrow(()->new ServiceException("Remove collector from club","Club doesn't exist"));
        club.removeCollector(collector);
        collector.removeClub(club);
        clubRepository.save(club);
        collectorRepository.save(collector);
    }




}
