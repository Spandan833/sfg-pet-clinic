package com.springframework.sfgpetclinic.bootstrap;

import com.springframework.sfgpetclinic.model.*;
import com.springframework.sfgpetclinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;
    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
                      SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }


    @Override
    public void run(String... args) throws Exception {
        int count = petTypeService.findAll().size();
        if(count == 0){
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("dog");

        PetType cat = new PetType();
        cat.setName("cat");

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");

        Speciality surgery = new Speciality();
        surgery.setDescription("surgery");

        Speciality dentistry = new Speciality();
        dentistry.setDescription("dentistry");

        Speciality savedRadiology = specialityService.save(radiology);
        Speciality savedSurgery = specialityService.save(surgery);
        Speciality savedDentisty = specialityService.save(dentistry);

        dog = petTypeService.save(dog);
        cat = petTypeService.save(cat);
        Owner mike = new Owner();
        mike.setFirstName("Michael");
        mike.setLastName("Weston");
        mike.setAddress("8/1 Hospital Road, Muktadhara auditorium");
        mike.setCity("Agartala");
        mike.setTelephone("0123456789");

        Owner joy = Owner.builder().firstName("Joy").pets(new HashSet<>()).build();
        ownerService.save(joy);
        Pet mikesPet = new Pet();
        mikesPet.setName("Mikes Pet");
        mikesPet.setPetType(dog);
        mikesPet.setOwner(mike);
        mikesPet.setBirthDate(LocalDate.now());

        mike.getPets().add(mikesPet);

        ownerService.save(mike);

        Visit mikesPetVisit = new Visit();
        mikesPetVisit.setPet(mikesPet);
        mikesPetVisit.setDescription("Mikes pet visit");
        mikesPetVisit.setDate(LocalDate.now());
        mikesPetVisit = visitService.save(mikesPetVisit);

        for(Visit visit : visitService.findAll()){
            System.out.println(visit.getDescription());
        }

        Owner fiona = new Owner();
        fiona.setFirstName("Fiona");
        fiona.setLastName("Glenanne");
        fiona.setAddress("8/1 Hospital Road, Muktadhara auditorium");
        fiona.setCity("Agartala");
        fiona.setTelephone("0123456789");
        //ownerService.save(fiona);


        Pet fionasCat = new Pet();

        fionasCat.setOwner(fiona);
        fionasCat.setPetType(cat);
        fionasCat.setName("Just Cat");
        fionasCat.setBirthDate(LocalDate.now());

        fiona.getPets().add(fionasCat);
        ownerService.save(fiona);
        for(Owner owner : ownerService.findAll()){
            System.out.println(owner.getFirstName());
        }

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(savedRadiology);

        vetService.save(vet1);

        Vet vet2 = new Vet();

        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(savedSurgery);

        vetService.save(vet2);

        System.out.println("Loaded Vets....");
    }
}
