package com.springframework.sfgpetclinic.controllers;

import com.springframework.sfgpetclinic.model.Owner;
import com.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    OwnerController controller;

    @Mock
    OwnerService ownerService;

    Set<Owner> owners;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(ownerService);
        this.controller = new OwnerController(ownerService);
        this.owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).firstName("Roderick").lastName("Usher").build());
        owners.add(Owner.builder().id(2L).firstName("Riley").lastName("Flynn").build());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getOwners() throws Exception {
        when(ownerService.findAll()).thenReturn(owners);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index.html"))
                .andExpect(model().attribute("owners",hasSize(2)));
    }

    @Test
    void getOwnersByIndex() throws Exception {
        when(ownerService.findAll()).thenReturn(owners);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index.html"))
                .andExpect(model().attribute("owners",hasSize(2)));
    }

    @Test
    void findOwner() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("notimplemented.html"));

        Mockito.verifyNoInteractions(ownerService);
    }
    @Test
    void displayOwnerById() throws Exception{
        when(ownerService.findById(anyLong()))
                .thenReturn(owners.stream().filter(owner -> owner.getId().equals(1L))
                .findAny().get());

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner",hasProperty("id",is(1L))));

    }
}