package com.springframework.sfgpetclinic.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="vets")
public class Vet extends Person{
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="vet_specialities", joinColumns = @JoinColumn(name="vet_id"),inverseJoinColumns = @JoinColumn(name="speciality_id"))
    private Set<Speciality> speciality = new HashSet<>();

    public Set<Speciality> getSpecialities() {
        return speciality;
    }

    public void setSpeciality(Set<Speciality> speciality) {
        this.speciality = speciality;
    }
}
