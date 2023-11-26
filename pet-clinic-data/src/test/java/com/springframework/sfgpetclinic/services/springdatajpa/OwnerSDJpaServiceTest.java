package com.springframework.sfgpetclinic.services.springdatajpa;

import com.springframework.sfgpetclinic.model.Owner;
import com.springframework.sfgpetclinic.repositories.OwnerRepository;
import com.springframework.sfgpetclinic.repositories.PetRepository;
import com.springframework.sfgpetclinic.repositories.PetTypeRepository;
import com.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    private final String LAST_NAME = "Cole";
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    Owner returnOwner;

    @InjectMocks
    OwnerSDJpaService ownerService;


    @BeforeEach
    void setUp() {
        this.returnOwner = Owner.builder().id(1L).firstName("Rustin").lastName("Cole").build();
    }

    @Test
    void findAll() {
        Set<Owner> ownerData = new HashSet<>();
        ownerData.add(returnOwner);
        Mockito.when(ownerRepository.findAll()).thenReturn(ownerData);

        Set<Owner> owners = ownerService.findAll();

        Assertions.assertEquals(owners.size(), ownerData.size());
    }

    @Test
    void findById() {
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(returnOwner));

        Owner owner = ownerService.findById(1L);

        Assertions.assertEquals(returnOwner.getId(),owner.getId());
        verify(ownerRepository,Mockito.times(1)).findById(eq(1L));

    }

    @Test
    void findByIdNotFound() {
        Mockito.when(ownerRepository.findById(any())).thenReturn(Optional.empty());

        Owner owner = ownerService.findById(2L);

        Assertions.assertNull(owner);

    }

    @Test
    void save() {
        Mockito.when(ownerRepository.save(returnOwner)).thenReturn(returnOwner);
        Owner savedOwner = ownerService.save(returnOwner);

        Assertions.assertNotNull(savedOwner);
        Assertions.assertEquals(savedOwner.getId(),returnOwner.getId());
        verify(ownerRepository,Mockito.times(1)).save(eq(returnOwner));
    }

    @Test
    void delete() {
        ownerService.delete(returnOwner);

        verify(ownerRepository,times(1)).delete(eq(returnOwner));
    }

    @Test
    void deleteById() {
        ownerService.deleteById(2L);

        verify(ownerRepository,times(1)).deleteById(eq(2L));
    }

    @Test
    void findByLastName() {
        Owner owner = Owner.builder().id(1L).firstName("Ian").lastName(LAST_NAME).build();

        Mockito.when(ownerRepository.findByLastName(any())).thenReturn(owner);

        Owner kochinski = ownerService.findByLastName(LAST_NAME);

        Assertions.assertNotNull(kochinski);
        Assertions.assertEquals(owner.getLastName(), kochinski.getLastName());
        verify(ownerRepository, Mockito.times(1)).findByLastName(eq(LAST_NAME));
    }
}