package ucll.be.ip.minor.groep1210;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ucll.be.ip.minor.groep1210.model.Collector;
import ucll.be.ip.minor.groep1210.model.CollectorRepository;
import ucll.be.ip.minor.groep1210.model.MuntCollectie;
import ucll.be.ip.minor.groep1210.model.MuntCollectieRepository;

@Service
public class CollectorService {

    @Autowired
    private CollectorRepository collectorRepository;

    public Collector addCollector(Collector collector){
        if(collectorRepository.existsCollectorByNaam(collector.getNaam())){
            throw new ServiceException("add","collector.exist");
        }

        return collectorRepository.save(collector);
    }

    public Iterable<Collector> getAllCollectors() {
        return collectorRepository.findAll();
    }

    public Iterable<Collector> findCollectorsByRegio(String regio) {
        return collectorRepository.findAllByRegio(regio);
    }

    public Iterable<Collector> findCollectorsByNaamVoorNaam(String naam, String voorNaam) {
        return collectorRepository.findAllByNaamAndVoornaam(naam,voorNaam);
    }

    public void deleteCollectorWithId(long id) {
        Collector collector = collectorRepository.findById(id).orElseThrow(()-> new ServiceException("Delete collector","Collector doesn't exist"));
        if (collector.getClubs().size() > 0) throw new ServiceException("Delete collector","Collector has clubs");
        collectorRepository.delete(collector);
    }

    public void updateCollector(Collector collector){
        String naam = collector.getNaam();
        String voorNaam = collector.getVoornaam();
        String regio = collector.getRegio();
        long id = collector.getId();

        try{
            Collector collectorFound = collectorRepository.getById(id);
            collectorFound.setNaam(naam);
            collectorFound.setVoornaam(voorNaam);
            collectorFound.setRegio(regio);

            collectorRepository.save(collectorFound);

        }catch(Exception e){
            throw new ServiceException("update","collector.update.error");
        }
    }
}
