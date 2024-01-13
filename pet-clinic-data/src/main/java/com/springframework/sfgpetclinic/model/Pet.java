package com.springframework.sfgpetclinic.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Pets")
public class Pet extends BaseEntity{
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
