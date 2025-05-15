package com.bpifrance.beneficiaryapi.integration;

import com.bpifrance.beneficiaryapi.dto.BeneficiaireDto;
import com.bpifrance.beneficiaryapi.dto.EntrepriseDto;
import com.bpifrance.beneficiaryapi.dto.PersonnePhysiqueDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testFullWorkflow() {
        // Créer une entreprise
        ResponseEntity<Void> entrepriseResp = restTemplate.postForEntity(baseUrl() + "/entreprises",
                new EntrepriseDto("MaBoite"), Void.class);
        assertEquals(HttpStatus.CREATED, entrepriseResp.getStatusCode());

        URI entrepriseUri = entrepriseResp.getHeaders().getLocation();
        assertNotNull(entrepriseUri);

        // Créer une personne
        PersonnePhysiqueDto personneDto = new PersonnePhysiqueDto("Dupont", "Jean", LocalDate.of(1985, 6, 12));
        ResponseEntity<Void> personneResp = restTemplate.postForEntity(baseUrl() + "/personnes", personneDto, Void.class);
        assertEquals(HttpStatus.CREATED, personneResp.getStatusCode());

        URI personneUri = personneResp.getHeaders().getLocation();
        assertNotNull(personneUri);

        UUID personneId = UUID.fromString(personneUri.getPath().split("/")[2]);

        // Ajouter la personne comme bénéficiaire
        BeneficiaireDto benef = new BeneficiaireDto(personneId, 30.0);
        ResponseEntity<Void> benefResp = restTemplate.postForEntity(entrepriseUri + "/beneficiaires", benef, Void.class);
        assertEquals(HttpStatus.CREATED, benefResp.getStatusCode());

        // Vérifier les bénéficiaires
        ResponseEntity<List> listResp = restTemplate.getForEntity(entrepriseUri + "/beneficiaires?type=effectifs", List.class);
        assertEquals(HttpStatus.OK, listResp.getStatusCode());
        assertFalse(listResp.getBody().isEmpty());
    }
}
