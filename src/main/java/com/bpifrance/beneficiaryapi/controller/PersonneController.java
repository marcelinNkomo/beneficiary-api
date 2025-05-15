package com.bpifrance.beneficiaryapi.controller;

import com.bpifrance.beneficiaryapi.dto.PersonnePhysiqueDto;
import com.bpifrance.beneficiaryapi.model.PersonnePhysique;
import com.bpifrance.beneficiaryapi.service.PersonneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/personnes")
@RequiredArgsConstructor
public class PersonneController {

    private final PersonneService service;

    @PostMapping
    public ResponseEntity<Void> creer(@RequestBody PersonnePhysiqueDto dto) {
        UUID id = service.creerPersonne(dto.nom(), dto.prenom(), dto.dateNaissance());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public PersonnePhysique get(@PathVariable UUID id) {
        return service.getPersonne(id);
    }
}
