package com.springframework.sfgpetclinic.controllers;

import com.springframework.sfgpetclinic.model.Owner;
import com.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result,Model model){
        if(owner.getLastName() == null){
            owner.setLastName("");
        }

        List<Owner> ownerList = ownerService.findAllByLastNameLike("%"+owner.getLastName()+"%");

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
