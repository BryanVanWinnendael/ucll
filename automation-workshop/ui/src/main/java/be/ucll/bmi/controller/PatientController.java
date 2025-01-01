package be.ucll.bmi.controller;

import be.ucll.bmi.model.Examination;
import be.ucll.bmi.model.Patient;
import be.ucll.bmi.service.PatientService;
import be.ucll.bmi.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class PatientController implements WebMvcConfigurer {

    @Autowired
    private PatientService patientService;

    @GetMapping("/")
    public String defaultPage() {
        return "redirect:/patients";
    }

    @GetMapping("/patients")
    public String getPatientsOverview(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patient/patients-overview";
    }

    @GetMapping("/patient/{ssn}")
    public String viewPatient(@PathVariable String ssn, Model model) {
        try {
            model.addAttribute("patient", patientService.findPatient(ssn));

        } catch (ServiceException e){
            model.addAttribute("error", e.getMessage());
        }

        return "patient/view-patient";
    }

    @GetMapping("/patient/register")
    public String getPatientRegisterForm(Model model) {
        if(!model.containsAttribute("patient")) {
            Patient patient = new Patient();
            patient.addExamination(new Examination());
            model.addAttribute("patient", patient);
        }

        return "patient/register-patient";
    }

    @PostMapping("/patient/register")
    public String registerPatient(@Valid Patient patient, BindingResult bindingResult, RedirectAttributes attr) {
        if(!bindingResult.hasErrors()){
            try {
                patientService.registerPatient(patient);

                return "redirect:/patient/" + patient.getSocialSecurityNumber() + "?registered=true";

            } catch (ServiceException e){
                attr.addFlashAttribute("error", e.getMessage());
            }

        } else {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.patient", bindingResult);
        }

        attr.addFlashAttribute("patient", patient);

        return "redirect:/patient/register";
    }

    @GetMapping("/patient-examination/{ssn}")
    public String getAddExaminationForm(@PathVariable String ssn, Model model) {
        if(!model.containsAttribute("patient")) {
            try {
                Patient patient = patientService.findPatient(ssn);
                Patient p = new Patient(patient.getSocialSecurityNumber(), patient.getBirthDate(), patient.getGender());
                p.addExamination(new Examination());
                model.addAttribute("patient", p);

            } catch (ServiceException e){
                model.addAttribute("error", e.getMessage());
                return "patient/view-patient";
            }
        }

        return "patient/add-examination";
    }

    @PostMapping("/patient/examination")
    public String addExamination(@Valid Patient patient, BindingResult bindingResult, RedirectAttributes attr) {
        String socialSecurityNumber = patient.getSocialSecurityNumber();

        if(!bindingResult.hasErrors()){
            patientService.addExamination(socialSecurityNumber, patient.getExaminations().get(0));

            return "redirect:/patient/" + socialSecurityNumber;
        }

        attr.addFlashAttribute("org.springframework.validation.BindingResult.patient", bindingResult);
        attr.addFlashAttribute("patient", patient);

        return "redirect:/patient-examination/" + socialSecurityNumber;
    }
}
