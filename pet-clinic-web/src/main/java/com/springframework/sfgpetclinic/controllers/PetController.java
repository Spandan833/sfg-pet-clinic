package com.springframework.sfgpetclinic.controllers;

import com.springframework.sfgpetclinic.model.Owner;
import com.springframework.sfgpetclinic.model.PetType;
import com.springframework.sfgpetclinic.services.OwnerService;
import com.springframework.sfgpetclinic.services.PetService;
import com.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {
    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;
    public  PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService){
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
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
}
