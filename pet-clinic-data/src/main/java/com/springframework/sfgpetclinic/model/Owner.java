package com.springframework.sfgpetclinic.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="owners")
public class Owner extends Person{
    @Builder
    public Owner(Long id, String firstName, String lastName, Set<Pet> pets, String address, String city, String telephone) {
        super(id, firstName, lastName);
        if(pets != null) this.pets = pets;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
    }

    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL)
    private Set<Pet> pets = new HashSet<>();

    private String address;

    private String city;

    private String telephone;

    public Pet getPet(String name){
        return this.getPet(name,false);
    }

    public Pet getPet(String name, boolean ignoreNew) {
        name = name.toLowerCase();

        for(Pet pet : pets){
            if(!ignoreNew || !pet.isNew()){
                String compName = pet.getName();
                compName = compName.toLowerCase();
                if(compName.equals((name))){
                    return pet;
                }
            }
        }
        return null;
    }
}

