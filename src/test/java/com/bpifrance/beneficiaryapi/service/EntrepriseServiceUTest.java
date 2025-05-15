package com.bpifrance.beneficiaryapi.service;

import com.bpifrance.beneficiaryapi.repository.InMemoryStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EntrepriseServiceUTest {
    private EntrepriseService entrepriseService;
    private InMemoryStore store;

    @BeforeEach
    public void setup() {
        store = new InMemoryStore();
        entrepriseService = new EntrepriseService(store);
    }

    @Test
    public void testCreerEntreprise() {
        UUID id = entrepriseService.creerEntreprise("TestEntreprise");
        assertNotNull(store.getEntreprises().get(id));
    }

    @Test
    public void testGetEntrepriseNotFound() {
        UUID id = UUID.randomUUID();
        assertThrows(ResponseStatusException.class, () -> entrepriseService.getEntreprise(id));
    }

    @Test
    public void testAjouterBeneficiaireValide() {
        UUID entrepriseId = entrepriseService.creerEntreprise("TestEntreprise");
        UUID benefId = UUID.randomUUID();
        entrepriseService.ajouterBeneficiaire(entrepriseId, benefId, 40.0);
        assertEquals(40.0, store.getEntreprises().get(entrepriseId).getBeneficiaires().get(benefId));
    }

    @Test
    public void testAjouterBeneficiaireDepassement() {
        UUID entrepriseId = entrepriseService.creerEntreprise("TestEntreprise");
        UUID b1 = UUID.randomUUID();
        UUID b2 = UUID.randomUUID();
        entrepriseService.ajouterBeneficiaire(entrepriseId, b1, 60.0);
        entrepriseService.ajouterBeneficiaire(entrepriseId, b2, 30.0);
        assertThrows(ResponseStatusException.class, () -> entrepriseService.ajouterBeneficiaire(entrepriseId, UUID.randomUUID(), 20.0));
    }
}