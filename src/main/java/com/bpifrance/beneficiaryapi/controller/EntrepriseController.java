package com.bpifrance.beneficiaryapi.controller;

import com.bpifrance.beneficiaryapi.dto.BeneficiaireDto;
import com.bpifrance.beneficiaryapi.dto.EntrepriseDto;
import com.bpifrance.beneficiaryapi.model.Entreprise;
import com.bpifrance.beneficiaryapi.service.EntrepriseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/entreprises")
@RequiredArgsConstructor
public class EntrepriseController {

    private final EntrepriseService service;

    @PostMapping
    public ResponseEntity<Void> creer(@RequestBody EntrepriseDto dto) {
        UUID id = service.creerEntreprise(dto.nom());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public Entreprise get(@PathVariable UUID id) {
        return service.getEntreprise(id);
    }

    @GetMapping("/{id}/beneficiaires")
    public List<String> beneficiaires(@PathVariable UUID id, @RequestParam(defaultValue = "tous") String type) {
        return service.getBeneficiaires(id, type);
    }

    @PostMapping("/{id}/beneficiaires")
    public ResponseEntity<Void> ajouter(@PathVariable UUID id, @RequestBody BeneficiaireDto dto) {
        service.ajouterBeneficiaire(id, dto.beneficiaireId(), dto.pourcentage());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
