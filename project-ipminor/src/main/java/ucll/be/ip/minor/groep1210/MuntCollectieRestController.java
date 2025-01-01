package ucll.be.ip.minor.groep1210;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ucll.be.ip.minor.groep1210.model.Coin;
import ucll.be.ip.minor.groep1210.model.MuntCollectie;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/collection")
public class MuntCollectieRestController {

    @Autowired
    private MuntCollectieService service;

    @GetMapping("/overview")
    public Iterable<MuntCollectie> getAll (Pageable firstPageWithTwoElements){
        return service.findAllMuntCollecties(firstPageWithTwoElements);
    }

    @PutMapping("/update/{id}")
    public void update(@RequestBody @PathVariable("id") long id,@Valid MuntCollectie muntCollectie){
        service.updateMuntCollectie(muntCollectie);
    }

    @GetMapping("/sort/country")
    public Iterable<MuntCollectie> muntcollectieoverviewSortByLand (Model model) {
        return service.findAllSortByLand();
    }

    @GetMapping("/search/year/{year}")
    public Iterable<MuntCollectie> muntcollectieSearchByYear(@PathVariable("year") Long year){
        return service.findAllBySearchYear(year);
    }

    @GetMapping("/{collection_id}/getCoins")
    public Iterable<Coin> getAllCoins(@PathVariable("collection_id") long id) {
        return service.getAllCoins(id);
    }

    @DeleteMapping("/removeCoin/{collection_id}")
    public void removeCoin(@PathVariable("collection_id") long id, @RequestParam("coinId") long coinId) {
        service.removeCollection(coinId, id);
    }

    @PostMapping("/addCoin/{collection_id}")
    public void addCoin(@PathVariable("collection_id") long id, @RequestParam("coinId") long coinId) {
        service.setCoinToCollection(coinId, id);
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
