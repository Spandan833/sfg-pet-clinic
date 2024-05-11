package com.springframework.sfgpetclinic.controllers;

import com.springframework.sfgpetclinic.formatters.PetTypeFormatter;
import com.springframework.sfgpetclinic.model.Owner;
import com.springframework.sfgpetclinic.model.Pet;
import com.springframework.sfgpetclinic.model.PetType;
import com.springframework.sfgpetclinic.services.OwnerService;
import com.springframework.sfgpetclinic.services.PetService;
import com.springframework.sfgpetclinic.services.PetTypeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Collection;

import static java.lang.Long.parseLong;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {
    public static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    private final PetTypeFormatter petTypeFormatter;
    public  PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService, PetTypeFormatter petTypeFormatter){
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
        this.petTypeFormatter = petTypeFormatter;
    }
    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes(){
        return this.petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") long ownerId){
        return this.ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, Model model) {
        Pet pet = new Pet();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model,
                                      RedirectAttributes redirectAttributes) {
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null){
            result.rejectValue("name", "duplicate", "already exists");
        }
        owner.getPets().add(pet);
        pet.setOwner(owner);
        if (result.hasErrors()) {
            model.put("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            petService.save(pet);

            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable long petId, ModelMap model){
        Pet pet = petService.findById(petId);
        model.addAttribute("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, Model model){
        if(result.hasErrors()){
            pet.setOwner(owner);
            model.addAttribute("pet",pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        }
        owner.getPets().add(pet);
        pet.setOwner(owner);
        petService.save(pet);
        return "redirect:/owners/"+owner.getId();
    }
}
