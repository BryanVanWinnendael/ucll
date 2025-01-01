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

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/coin")
public class CoinRestController {

    @Autowired
    private CoinService service;

    @GetMapping("/overview")
    public Iterable<Coin> getAll(){
        return service.getAllCoins();
    }

    @PostMapping("/add")
    public void add(@RequestBody @Valid Coin coin){
        service.addCoin(coin);
    }

    @PutMapping("/update/{id}")
    public void update(@RequestBody @PathVariable("id") long id,@Valid Coin coin){
        service.updateCoin(coin);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") long id){
        service.deleteCoinWithId(id);
    }

    @GetMapping("/search/year/{year}")
    public Iterable<Coin> searchCoinWithGivenYear(@PathVariable("year") int year){
        return service.getAllCoinsWithGivenYear(year);
    }

    @GetMapping("/search/country")
    public Iterable<Coin> searchCoinWithGivenCountry(@RequestParam("value") String country){
        return service.getAllCoinsWithLandContains(country);
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
