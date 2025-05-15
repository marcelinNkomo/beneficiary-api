package com.bpifrance.beneficiaryapi.dto;

import java.time.LocalDate;

public record PersonnePhysiqueDto(String nom, String prenom, LocalDate dateNaissance) {
}
