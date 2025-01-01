package ucll.be.ip.minor.groep1210;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ucll.be.ip.minor.groep1210.model.Club;
import ucll.be.ip.minor.groep1210.model.Collector;
import ucll.be.ip.minor.groep1210.model.MuntCollectie;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/collector")
public class CollectorRestController {

    @Autowired
    private CollectorService service;

    @PostMapping("/add")
    public void addCollector (@Valid @RequestBody Collector collector){
        service.addCollector(collector);
    }

    @GetMapping("/overview")
    public Iterable<Collector> getAllCollectors (){
        return service.getAllCollectors();
    }

    @PutMapping("/update/{id}")
    public void updateCollector(@RequestBody @PathVariable("id") long id, @Valid Collector collector){
        service.updateCollector(collector);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCollector(@PathVariable("id") long id){
        service.deleteCollectorWithId(id);
    }

    @GetMapping("/search/region")
    public Iterable<Collector> findCollectorsByRegion(@RequestParam("value") String regio){
        return service.findCollectorsByRegio(regio);
    }

    @GetMapping("/search/nameAndFirstname")
    public Iterable<Collector> findCollectorsByRegion(@RequestParam("name") String name, @RequestParam("firstname") String firstname){
        return service.findCollectorsByNaamVoorNaam(name,firstname);
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