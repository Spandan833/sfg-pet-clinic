package com.springframework.sfgpetclinic.controllers;

import com.springframework.sfgpetclinic.model.Owner;
import com.springframework.sfgpetclinic.services.OwnerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

//    @GetMapping({"/","","/index","/index.html"})
//    public String getOwners(Model model){
//        model.addAttribute("owners", ownerService.findAll());
//        return "owners/index.html";
//    }

    @GetMapping({"/find"})
    public String findOwners(Model model){
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping({"/new"})
    public String getNewOwnerForm(Model model){
        model.addAttribute("owner",Owner.builder().build());
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/new")
    public String createNewOwner(@Valid Owner owner, BindingResult result){
        if(result.hasErrors()){
            return "owners/createOrUpdateOwnerForm";
        }
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/"+savedOwner.getId();
    }

    @GetMapping("/{ownerId}/edit")
    public String getUpdateOwnerForm(@PathVariable Long ownerId, Model model){
        Owner owner = ownerService.findById(ownerId);
        model.addAttribute("owner",owner);
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwner(@Valid Owner owner, BindingResult result, @PathVariable Long ownerId){
        if(!result.hasErrors()){
            owner.setId(ownerId);
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
        return "/owners/createOrUpdateOwnerForm";


    }
    @GetMapping
    public String processFindForm(Owner owner, BindingResult result,Model model){
        if(owner.getLastName() == null){
            owner.setLastName("");
        }

        List<Owner> ownerList = ownerService.findAllByLastNameLike(owner.getLastName());

        if(ownerList.isEmpty()){
            result.rejectValue("lastName","notFound", "Not found");
            return "owners/findOwners";
        }
        else if(ownerList.size() == 1){
            owner = ownerList.iterator().next();
            return "redirect:/owners/"+owner.getId();
        }
        else{
            model.addAttribute("selections",ownerList);
            return "owners/ownerList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long id){
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(this.ownerService.findById(id));
        return mav;
    }
}
