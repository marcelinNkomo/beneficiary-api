package com.bpifrance.beneficiaryapi.repository;

import com.bpifrance.beneficiaryapi.model.Entreprise;
import com.bpifrance.beneficiaryapi.model.PersonnePhysique;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryStore {

    private final Map<UUID, Entreprise> entreprises = new ConcurrentHashMap<>();
    private final Map<UUID, PersonnePhysique> personnes = new ConcurrentHashMap<>();

    public Map<UUID, Entreprise> getEntreprises() {
        return entreprises;
    }

    public Map<UUID, PersonnePhysique> getPersonnes() {
        return personnes;
    }
}
