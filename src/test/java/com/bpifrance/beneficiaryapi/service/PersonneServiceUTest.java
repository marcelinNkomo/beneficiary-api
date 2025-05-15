package com.bpifrance.beneficiaryapi.service;

import com.bpifrance.beneficiaryapi.repository.InMemoryStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonneServiceUTest {
    private PersonneService personneService;
    private InMemoryStore store;

    @BeforeEach
    public void setup() {
        store = new InMemoryStore();
        personneService = new PersonneService(store);
    }

    @Test
    public void testCreerPersonne() {
        UUID id = personneService.creerPersonne("Doe", "John", LocalDate.of(1990, 1, 1));
        assertNotNull(store.getPersonnes().get(id));
    }

    @Test
    public void testGetPersonneNotFound() {
        UUID id = UUID.randomUUID();
        assertThrows(ResponseStatusException.class, () -> personneService.getPersonne(id));
    }
}