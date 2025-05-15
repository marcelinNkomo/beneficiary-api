package com.bpifrance.beneficiaryapi.service;

import com.bpifrance.beneficiaryapi.model.PersonnePhysique;
import com.bpifrance.beneficiaryapi.repository.InMemoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonneService {
    private final InMemoryStore store;

    public UUID creerPersonne(String nom, String prenom, LocalDate dateNaissance) {
        UUID id = UUID.randomUUID();
        store.getPersonnes().put(id, new PersonnePhysique(id, nom, prenom, dateNaissance));
        return id;
    }

    public PersonnePhysique getPersonne(UUID id) {
        return Optional.ofNullable(store.getPersonnes().get(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
