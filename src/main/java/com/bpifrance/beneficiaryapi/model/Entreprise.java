package com.bpifrance.beneficiaryapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.UUID;


@Data
@AllArgsConstructor
public class Entreprise {
    private UUID id;
    private String nom;
    private Map<UUID, Double> beneficiaires; // beneficiaireId -> pourcentage
}
