package com.springframework.sfgpetclinic.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="Pets")
public class Pet extends BaseEntity{
    private String name;
    @ManyToOne
    @JoinColumn(name="pet_id")
    private PetType petType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name="owner_id")
    private Owner owner;
    @Column(name="birthDate")
    private LocalDate birthDate;

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
