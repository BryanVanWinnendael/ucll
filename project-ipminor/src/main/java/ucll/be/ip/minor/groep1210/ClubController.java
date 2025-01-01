package ucll.be.ip.minor.groep1210;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ucll.be.ip.minor.groep1210.model.Club;
import ucll.be.ip.minor.groep1210.model.ClubRepository;

import javax.validation.Valid;
import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ClubController {

    @Autowired
    private ClubService service;

    @GetMapping("/club-overview")
    public String cluboverview (Model model, @RequestParam Optional<Integer> page) {
        Pageable firstPageWithFiveElements = PageRequest.of(page.orElse(0), 5);
        model.addAttribute("pages", service.getPages());
        model.addAttribute("club", service.findAllClubs(firstPageWithFiveElements));
        model.addAttribute("page",page.orElse(0));

        ArrayList<String> searchvalues = new ArrayList<>();
        searchvalues.add("Naam");
        searchvalues.add("Max aantal leden");
        searchvalues.add("Max aantal leden en hoger");
        model.addAttribute("searchValues",searchvalues);

        return "club-overview";
    }

    @GetMapping("/club-overviewOpNaam")
    public String cluboverviewOpNaam (Model model) {
        model.addAttribute("club", service.findAllClubsSortByName());
        return "club-overview";
    }

    @GetMapping("/club-overviewOpMaxAantalDeelnemers")
    public String cluboverviewOpMaxAantalDeelnemers (Model model) {
        model.addAttribute("club", service.findAllClubsSortByMaxAantalLeden());
        return "club-overview";
    }

    @GetMapping("/club-add")
    public String clubadd (Club club) {
        return "club-add";
    }

    @PostMapping("/club-add")
    public String clubadd(@Valid Club club, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "club-add";
        }
        try {
            service.addClub(club);
        }
        catch (ServiceException exc) {
            model.addAttribute("error", exc.getMessage());
            return "club-add";
        }


        return "redirect:/club-overview";
    }

    @GetMapping("/club-update/{id}")
    public String clubupdate (@PathVariable("id") Long id ,Model model) {
        try {
            model.addAttribute(service.findClubById(id));
        }
        catch (IllegalArgumentException exc) {
            model.addAttribute("error", exc.getMessage());
        }
        return "club-update";
    }

    @PostMapping("/club-update/{id}")
    public String update (@PathVariable("id") Long id, @Valid Club club, BindingResult result, Model model) {
        service.updateClub(club);
        return "redirect:/club-overview";
    }

    @GetMapping("/club-delete/{id}")
    public String clubdelete (@PathVariable("id") Long id) {
        try {
            service.deleteClubWithId(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/club-overview";
    }

    @GetMapping("/club-delete-confirm/{id}")
    public String clubdeleteconfirm (@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("club", service.findClubById(id));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return "club-delete";
    }

    @GetMapping("/club-search")
    public String clubsearch (@RequestParam(name = "searchValue", required = false) String searchValue, @RequestParam(name = "search", required = false) String search, Model model) {
        model.addAttribute("previousSearch",searchValue);
        model.addAttribute("previousValue",search);
        model.addAttribute("searched",true);
        Pageable firstPageWithFiveElements = PageRequest.of(0, 5);

        switch (searchValue) {
            case "Naam":
                if(!search.isEmpty()){
                    model.addAttribute("club", service.findAllBySearcName(search));
                }else{
                    model.addAttribute("errorString", "error");
                    model.addAttribute("club", service.findAllClubs(firstPageWithFiveElements));
                }
                break;
            case "Max aantal leden":
                try {
                    model.addAttribute("club", service.findAllBySearchMaxAantal(Long.parseLong(search)));
                } catch (NumberFormatException e) {
                    model.addAttribute("errorString", "error");
                    model.addAttribute("club", service.findAllClubs(firstPageWithFiveElements));
                    System.out.println(e);
                }
                break;
            case "Max aantal leden en hoger":
                try {
                    model.addAttribute("club", service.findAllBySearchMaxAantalEnHoger(Long.parseLong(search)));
                } catch (NumberFormatException e) {
                    model.addAttribute("errorString", "error");
                    model.addAttribute("club", service.findAllClubs(firstPageWithFiveElements));
                    System.out.println(e);
                }
                break;
            default:
                System.out.println("fout");
                break;
        }
        ArrayList<String> searchvalues = new ArrayList<>();
        searchvalues.add("Naam");
        searchvalues.add("Max aantal leden");
        searchvalues.add("Max aantal leden en hoger");
        model.addAttribute("searchValues",searchvalues);

        return "club-overview";
    }
}
