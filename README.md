# API de gestion des bénéficiaires effectifs

## Stack

- Java 21
- Spring Boot 3+
- Aucune base de données (stockage en mémoire)

## Pré-requis

- JDK-21
- Maven 3.9.9
- POSTMAN (ou une alternative)

## Lancer l'application

- Se positionner dans le répertoire du projet
- Se rassurer que la variable d'environnement JAVA_HOME est bien définit
- Exécuter la commande ci-dessous :

```bash
./mvnw spring-boot:run
````

L'application démarre sur http://localhost:8080.

## Endpoints

📮 Endpoints REST

1. ➕ Créer une entreprise

### POST **/entreprises**

{
"nom": "Ma Super Entreprise"
}

📤 Réponse :

    201 Created

    Header Location: /entreprises/{id}

2. 📄 Récupérer une entreprise

### GET **/entreprises/{id}**

📤 Réponse :

{
"id": "uuid",
"nom": "Ma Super Entreprise",
"beneficiaires": {
"uuid-beneficiaire": 30.0
}
}

    404 Not Found si l’entreprise n’existe pas

3. ➕ Créer une personne physique

### POST **/personnes**

{
"nom": "Dupont",
"prenom": "Jean",
"dateNaissance": "1985-06-12"
}

📤 Réponse :

    201 Created

    Header Location: /personnes/{id}

4. 👁️ Récupérer une personne physique

### GET **/personnes/{id}**

📤 Réponse :

{
"id": "uuid",
"nom": "Dupont",
"prenom": "Jean",
"dateNaissance": "1985-06-12"
}

    404 Not Found si personne non trouvée

5. 🔗 Ajouter un bénéficiaire à une entreprise

### POST **/entreprises/{id}/beneficiaires**

{
"beneficiaireId": "uuid",
"pourcentage": 30.0
}

📤 Réponse :

    201 Created

    404 Not Found si l’entreprise ou le bénéficiaire n’existe pas

    400 Bad Request si le total dépasse 100%

6. 📋 Récupérer les bénéficiaires d’une entreprise

### GET **/entreprises/{id}/beneficiaires?type=all|persons|effects**

    type=tous : tous les bénéficiaires (défaut)

    type=personnes : seulement les personnes physiques

    type=effectifs : personnes physiques > 25%

📤 Exemple de réponse :

[
"Jean Dupont : 30.0%",
"SARL Truc : 45.0%"
]

📥 Codes de retour :

    200 OK

    204 No Content si aucun bénéficiaire

    404 Not Found si l’entreprise est inconnue
