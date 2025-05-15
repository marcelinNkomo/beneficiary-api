package com.bpifrance.beneficiaryapi.dto;

import java.util.UUID;

public record BeneficiaireDto(UUID beneficiaireId, double pourcentage) {
}
