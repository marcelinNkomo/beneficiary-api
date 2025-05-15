package com.bpifrance.beneficiaryapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PersonnePhysique {
    private UUID id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
}
