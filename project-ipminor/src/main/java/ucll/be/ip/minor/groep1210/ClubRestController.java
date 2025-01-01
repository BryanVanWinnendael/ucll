package ucll.be.ip.minor.groep1210;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ucll.be.ip.minor.groep1210.model.Club;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import ucll.be.ip.minor.groep1210.model.Coin;
import ucll.be.ip.minor.groep1210.model.Collector;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/club")
public class ClubRestController {

    @Autowired
    private ClubService service;

    @GetMapping("/overview")
    public Iterable<Club> getAll(){
        return service.getAllClubs();
    }

    @PostMapping("/update/{id}")
    public void update(@RequestBody @PathVariable("id") long id,@Valid Club club){
        service.updateClub(club);
    }

    @GetMapping("/sortByName")
    public Iterable<Club> clubsOverviewSortByName () {
        return service.findAllClubsSortByName();
    }

    @GetMapping("/search/maxNumber/{aantal}")
    public Iterable<Club> clubsOverviewSearchByMaxAantal(@PathVariable("aantal") long aantal) {
        return service.findAllBySearchMaxAantal(aantal);
    }

    @DeleteMapping("/delete/{id}")
    public Iterable<Club> deleteClub (@PathVariable("id") long id) {
        service.deleteClubWithId(id);
        return service.getAllClubs();
    }


    @GetMapping("/getCollectors")
    public Iterable<Collector> getAllCollectors(@RequestParam("clubId") long id) {
        return service.getAllCollectors(id);
    }

    @DeleteMapping("/{club_id}/removeCollector/{collector_id}")
    public void removeCollector(@PathVariable("club_id") long id, @PathVariable("collector_id") long collectorId) {
        service.removeCollector(id, collectorId);
    }

    @PostMapping("/{club_id}/addCollector/{collector_id}")
    public void addCollector(@PathVariable("club_id") long id, @PathVariable("collector_id") long collectorId) {
        service.addCollector(id, collectorId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ServiceException.class, ResponseStatusException.class})
    public Map<String, String> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException)ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        }
        else if (ex instanceof ServiceException) {
            errors.put(((ServiceException) ex).getAction(), ex.getMessage());
        }
        else {
            errors.put(((ResponseStatusException)ex).getReason(), ex.getCause().getMessage());
        }
        return errors;
    }

}
