package com.springframework.sfgpetclinic.services.map;

import com.springframework.sfgpetclinic.model.Owner;
import com.springframework.sfgpetclinic.services.PetService;
import com.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class OwnerServiceMapTest {

    OwnerServiceMap ownerService;

    PetService petService;
    PetTypeService petTypeService;

    String lastName = "Kochinski";
    @BeforeEach
    void setUp() {
        this.petService = new PetServiceMap();
        this.petTypeService = new PetTypeServiceMap();
        this.ownerService = new OwnerServiceMap(petTypeService,petService);
    }

    @Test
    void OneOwner_FindAll_OneOwnerFound() {
        this.ownerService.save(Owner.builder().id(1L).firstName("Ruth").build());

        Set<Owner> owners = ownerService.findAll();

        Assertions.assertEquals(owners.size(), 1);
    }

    @Test
    void deleteById() {
        Long ownerId = 3L;
        Owner savedOwner = this.ownerService.save(Owner.builder().id(ownerId).build());
        Assertions.assertEquals(this.ownerService.findAll().size(),1);

        this.ownerService.deleteById(ownerId);

        Assertions.assertEquals(this.ownerService.findAll().size(),0);
    }

    @Test
    void deleteByObject() {
        Owner savedOwner = this.ownerService.save(Owner.builder().build());

        this.ownerService.delete(savedOwner);

        Assertions.assertEquals(this.ownerService.findAll().size(),0);
    }



    @Test
    void saveExistingId() {
        Assertions.assertEquals(ownerService.findAll().size(), 0);
        Owner owner = Owner.builder().id(2L).firstName("Matt").lastName("Frost").build();

        Owner savedOwner = this.ownerService.save(owner);

        Assertions.assertEquals(owner.getId(),savedOwner.getId());
        Assertions.assertEquals(ownerService.findAll().size(),1);
    }

    @Test
    void saveNoId(){
        Owner savedOwner = this.ownerService.save(Owner.builder().build());

        Assertions.assertNotNull(savedOwner);
        Assertions.assertNotNull(savedOwner.getId());
    }

    @Test
    void findById() {
        this.ownerService.save(Owner.builder().id(1L).firstName("Ruth").build());

        Owner owner = ownerService.findById(1L);

        Assertions.assertEquals(owner.getId(),1L);
    }

    @Test
    void findByLastName() {
        Owner owner = Owner.builder().id(1L).firstName("Ian").lastName(lastName).build();
        ownerService.save(owner);
        Owner ian = ownerService.findByLastName(lastName);

        Assertions.assertNotNull(ian);
        Assertions.assertEquals(1L,ian.getId());

    }
    @Test
    void findByLastNameNotFound() {
        Owner owner = Owner.builder().id(1L).firstName("Ian").lastName(lastName).build();
        ownerService.save(owner);

        Owner ian = ownerService.findByLastName("Wrong");

        Assertions.assertNull(ian);
    }

}