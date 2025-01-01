package ucll.be.ip.minor.groep1210;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import ucll.be.ip.minor.groep1210.model.Collector;
import ucll.be.ip.minor.groep1210.model.MuntCollectie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@Controller
public class MuntCollectieController {

    @Autowired
    private MuntCollectieService service;


    @GetMapping("/muntcollectie-overview")
    public String muntcollectieoverview (Model model, @RequestParam Optional<Integer> page) {
        ArrayList<String> searchvalues = new ArrayList<>();
        searchvalues.add("Land");
        searchvalues.add("Jaartal");
        searchvalues.add("Max Jaartal");
        model.addAttribute("searchValues",searchvalues);

        Pageable firstPageWithFiveElements = PageRequest.of(page.orElse(0), 5);
        model.addAttribute("pages", service.getPages());
        model.addAttribute("muntcollecties", service.findAllMuntCollecties(firstPageWithFiveElements));
        model.addAttribute("page",page.orElse(0));


        return "muntcollectie-overview";
    }

    @GetMapping("/muntcollectie-overviewByYear")
    public String muntcollectieoverviewSortByYear (Model model, @RequestParam Optional<Integer> page) {
        model.addAttribute("pages", service.getPages());
        model.addAttribute("page",page.orElse(0));
        model.addAttribute("muntcollecties", service.findAllSortByYear());

        ArrayList<String> searchvalues = new ArrayList<>();
        searchvalues.add("Land");
        searchvalues.add("Jaartal");
        searchvalues.add("Max Jaartal");
        model.addAttribute("searchValues",searchvalues);

        return "muntcollectie-overview";
    }

    @GetMapping("/muntcollectie-overviewByLand")
    public String muntcollectieoverviewSortByLand (Model model, @RequestParam Optional<Integer> page) {
        model.addAttribute("pages", service.getPages());
        model.addAttribute("page",page.orElse(0));
        model.addAttribute("muntcollecties", service.findAllSortByLand());

        ArrayList<String> searchvalues = new ArrayList<>();
        searchvalues.add("Land");
        searchvalues.add("Jaartal");
        searchvalues.add("Max Jaartal");
        model.addAttribute("searchValues",searchvalues);

        return "muntcollectie-overview";
    }

    @GetMapping("/muntcollectie-add")
    public String muntcollectieadd (MuntCollectie muntCollectie) {
        return "muntcollectie-add";
    }

    @PostMapping("/muntcollectie-add")
    public String muntcollectieadd(@Valid MuntCollectie muntCollectie, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "muntcollectie-add";
        }
        try {
            service.addMuntCollectie(muntCollectie);
        }
        catch (ServiceException exc) {
            model.addAttribute("error", exc.getMessage());
            return "muntcollectie-add";
        }

        return "redirect:/muntcollectie-overview?";
    }

    @GetMapping("/muntcollectie-update/{id}")
    public String muntcollectieupdate (@PathVariable("id") Long id,Model model) {
        try {
            model.addAttribute(service.findById(id));
        }
        catch (IllegalArgumentException exc) {
            model.addAttribute("error", exc.getMessage());
        }
        return "muntcollectie-update";
    }


    @PostMapping("/muntcollectie-update/{id}")
    public String update (@PathVariable("id") Long id, @Valid MuntCollectie muntCollectie, BindingResult result, Model model) {
        service.updateMuntCollectie(muntCollectie);
        return "redirect:/muntcollectie-overview";
    }

    @GetMapping("/muntcollectie-delete/{id}")
    public String muntcollectiedelete (@PathVariable("id") Long id) {
        try {
            service.deleteMuntCollectieWithId(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/muntcollectie-overview";
    }

    @GetMapping("/muntcollectie-delete-confirm/{id}")
    public String muntcollectiedeleteconfirm (@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("muntCollectie", service.findById(id));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return "muntcollectie-delete";
    }

    @GetMapping("/muntcollectie-search")
    public String muntcollectiesearch (@RequestParam(name = "searchValue", required = false) String searchValue, @RequestParam Optional<Integer> page, @RequestParam(name = "search", required = false) String search, Model model) {
        model.addAttribute("previousSearch",searchValue);
        model.addAttribute("previousValue",search);
        model.addAttribute("searched",true);
        Pageable firstPageWithFiveElements = PageRequest.of(0, 5);
        model.addAttribute("pages", service.getPages());
        switch (searchValue){
            case "Land":
                if(!search.isEmpty()){
                    model.addAttribute("muntcollecties", service.findAllBySearchLand(search));
                }else{
                    model.addAttribute("errorString", "error");
                    model.addAttribute("muntcollecties", service.findAllMuntCollecties(firstPageWithFiveElements));
                }
                break;
            case "Jaartal":
                try{
                    model.addAttribute("muntcollecties", service.findAllBySearchYear(Long.parseLong(search)));
                }catch (NumberFormatException e){
                    model.addAttribute("errorString", "error");
                    model.addAttribute("muntcollecties", service.findAllMuntCollecties(firstPageWithFiveElements));
                    System.out.println(e);
                }
                break;
            case "Max Jaartal":
                try{
                    model.addAttribute("muntcollecties", service.findAllBySearchYearIsBefore(Long.parseLong(search)));
                }catch (NumberFormatException  e){
                    model.addAttribute("errorString", "error");
                    model.addAttribute("muntcollecties", service.findAllMuntCollecties(firstPageWithFiveElements));
                    System.out.println(e);
                }
                break;
            default:
                System.out.println("fout");
                break;
        }

        ArrayList<String> searchvalues = new ArrayList<>();
        searchvalues.add("Land");
        searchvalues.add("Jaartal");
        searchvalues.add("Max Jaartal");
        model.addAttribute("searchValues",searchvalues);

        return "muntcollectie-overview";
    }

    @GetMapping("/fotos")
    public String fotos () {
        return "fotos";
    }

    @GetMapping("/overOns")
    public String overOns () {
        return "overOns";
    }
}
