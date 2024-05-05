package com.springframework.sfgpetclinic.controllers;

import com.springframework.sfgpetclinic.model.Owner;
import com.springframework.sfgpetclinic.model.Pet;
import com.springframework.sfgpetclinic.model.Visit;
import com.springframework.sfgpetclinic.repositories.OwnerRepository;
import com.springframework.sfgpetclinic.repositories.PetRepository;
import com.springframework.sfgpetclinic.repositories.VisitRepository;
import com.springframework.sfgpetclinic.services.PetService;
import com.springframework.sfgpetclinic.services.VisitService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class VisitController {
    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    /**
     * Called before each and every @RequestMapping annotated method. 2 goals: - Make sure
     * we always have fresh data - Since we do not use the session scope, make sure that
     * Pet object always has an id (Even though id is not part of the form fields)
     * @param petId
     * @return Pet
     */
    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") long petId, Map<String, Object> model) {
        Pet pet = petService.findById(petId);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        model.put("pet", pet);
        return visit;
    }

    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is
    // called
    @GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String initNewVisitForm() {
        return "pets/createOrUpdateVisitForm";
    }

    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is
    // called
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@ModelAttribute Owner owner, @PathVariable int petId, @Valid Visit visit,
                                      BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        }

        this.visitService.save(visit);
        return "redirect:/owners/{ownerId}";
    }
}
