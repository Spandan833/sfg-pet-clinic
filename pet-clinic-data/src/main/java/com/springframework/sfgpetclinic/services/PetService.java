
package com.springframework.sfgpetclinic.services;

import com.springframework.sfgpetclinic.model.Pet;

import java.util.Set;

public interface PetService extends CrudService<Pet,Long> {

    Pet findByLastName(String lastName);

}



