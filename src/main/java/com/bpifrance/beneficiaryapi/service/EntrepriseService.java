package com.bpifrance.beneficiaryapi.service;

import com.bpifrance.beneficiaryapi.model.Entreprise;
import com.bpifrance.beneficiaryapi.repository.InMemoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntrepriseService {

    private final InMemoryStore store;

    public UUID creerEntreprise(String nom) {
        UUID id = UUID.randomUUID();
        store.getEntreprises().put(id, new Entreprise(id, nom, new HashMap<>()));
        return id;
    }

    public Entreprise getEntreprise(UUID id) {
        return Optional.ofNullable(store.getEntreprises().get(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void ajouterBeneficiaire(UUID entrepriseId, UUID beneficiaireId, double pourcentage) {
        Entreprise entreprise = getEntreprise(entrepriseId);

        double total = entreprise.getBeneficiaires().values().stream().mapToDouble(Double::doubleValue).sum();
        if (total + pourcentage > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pourcentage total > 100%");
        }

        entreprise.getBeneficiaires().put(beneficiaireId, pourcentage);
    }

    public List<String> getBeneficiaires(UUID entrepriseId, String type) {
        Entreprise entreprise = getEntreprise(entrepriseId);

        if (entreprise.getBeneficiaires().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return entreprise.getBeneficiaires().entrySet().stream()
                .filter(e -> {
                    boolean estPersonne = store.getPersonnes().containsKey(e.getKey());
                    return switch (type) {
                        case "tous" -> true;
                        case "personnes" -> estPersonne;
                        case "effectifs" -> estPersonne && e.getValue() > 25;
                        default -> false;
                    };
                })
                .map(e -> {
                    String nom = Optional.ofNullable(store.getPersonnes().get(e.getKey()))
                            .map(p -> p.getPrenom() + " " + p.getNom())
                            .orElse("Entreprise " + e.getKey());
                    return nom + " : " + e.getValue() + "%";
                }).toList();
    }
}
