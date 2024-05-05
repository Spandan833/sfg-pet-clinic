package com.springframework.sfgpetclinic.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Pets")
public class Pet extends BaseEntity{
    @Builder
    public Pet(Long id, String name, PetType petType, Owner owner, LocalDate birthDate, Set<Visit> visits) {
        super(id);
        this.name = name;
        this.petType = petType;
        this.owner = owner;
        this.birthDate = birthDate;

        if (visits == null || visits.size() > 0) {
            this.visits = visits;
        }
    }
    private String name;

    @ManyToOne
    @JoinColumn(name="pet_id")
    private PetType petType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="owner_id")
    private Owner owner;

    @Column(name="birthDate")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "pet",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Visit> visits = new HashSet<>();
}
