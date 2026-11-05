# Journal — STATION404 (ALTN72, 2026-27)
**Groupe :** Ornella MOTENGO BAUTI, Melek USTUN
## README
- Prérequis : 
  - Java 21, 
  - Maven,
  - MariaDB
  - Docker et Docker Compose (déploiement)

- Initialiser la base : 
  - `db/base_station404.sql`
  - Variables et valeurs par défaut `src/main/ressources/application.properties`

- Lancer : 
  - `mvn spring-boot:run` (bouton Run)
  - Ouvrir http://localhost:8080
  - Docker : 
    - créer un fichier .env -> DB_ROOT_PASSWORD, DB_USER et DB_PASSWORD
    - `docker compose up -d --build`

- Application déployée : http://46.202.128.229/
- API : http://localhost:8080/swagger-ui/index.html

- Comptes de test : 
  - profil 1 : admin.sophie |ou| admin.karim / Admin404!
  - profil 2 : agent.julien |ou| agent.nadia / Agent404!
  
- Limites connues :
  - Tailwind CSS, Leaflet, Chart.js et les fonds de carte sont chargés depuis Internet (une connexion est nécessaire)

## ARCHITECTURE
- Dépôt : https://github.com/Ornella11/Station404
- Schéma : `docs/architecture_Station404.png`

## TESTS
- Stratégie :
  - Tests unitaires des règles métier -> indicateur d'urgence
  - Une base H2 en mémoire pour les tests
  - Clock injectée dans les services et heure courante passée en paramètre de EtatIncident.de(…), donc des tests reproductibles

- Commande : `mvn verify`
- Couverts : 
  - RG3 (incident non affecté ouvert depuis 47h, 48h, 71h, 72,)
  - RG4 (incident affecté depuis 80h, incident résolu)
  - ContextLoads
  - RG7 (historique ordonné des incidents)
  - RG11 (un agent qui tente d'affecter un prestataire -> erreur 403)
  
## CHOIX TECHNIQUES
- Droits par profil : (tableau du §5.6)
  - Consulter équipements et fiches = ADMIN - Accès complet || AGENT - Lecture seule
  - Déclarer un incident = ADMIN - Accès complet || AGENT - Accès complet
  - Consulter / modifier un incident = ADMIN - Accès complet || AGENT - Accès complet
  - Affecter ou réaffecter un prestataire = ADMIN - Accès complet || AGENT - Interdit
  - Déclarer un incident résolu = ADMIN - Accès complet || AGENT - Accès complet
  - Consulter l'historique = ADMIN - Accès complet || AGENT - Lecture seule
  - Évaluer un prestataire = ADMIN - Lecture seule || AGENT - Accès complet
  - Tableau de bord = ADMIN - Accès complet || AGENT - Lecture seule
  

- Sécurité
  - Spring Sécurity : module approprié de l'écosystème Spring pour gérer les connexion
  - Mot de passe hachés : jamais stockés en clair
  - UserDetails : `Utilisateur` implémente UserDetails -> évite une classe supplémentaire et risque limité
  - `th:action` pour la déconnexion qui passe par un `POST`
  
- Exceptions : 
  - @ControllerAdvice :
    - Les services lèvent des exceptions métier sans dépendre de HTTP
    - Deux gestionnaires (responsabilité unique)
  - ResponseStatusException :
    - Un code fixe par exception, l'annotation suffit
  - SOLID : 
    - S — Responsabilité unique 
      - La séparation contrôleur / service / repository : le contrôleur reçoit la requête, le service applique les règles, le repository accède aux données
      - Le calcul de l'indicateur d'urgence est isolé dans EtatIncident, avec les seuils de 48 h et 72 h en constantes nommées 
      - Deux gestionnaires d'erreurs distincts, l'un pour les pages, l'autre pour l'API
      - Un contrôleur par domaine fonctionnel (connexion, stations, équipements, cartographie, API) 
      - Les DTO (IncidentDto, EtatStation) définissent ce qui est exposé à la vue ou à l'API, indépendamment des entités
    - O — Ouvert / fermé
      - Le gestionnaire traite RessourceIntrouvableException, et non chacune de ses filles
    - L — Substitution de Liskov
      - Utilisateur implémente UserDetails et peut remplacer n'importe quel UserDetails attendu par Spring Security, sans changer son comportement
    - I — Ségrégation des interfaces
      - Les services sont petits et cohérents
    - D — Inversion des dépendances
      - Toutes les dépendances sont injectées par le constructeur
  - @Transactional : 
    - `EvaluationService.evaluer` : les vérifications (incident résolu et affecté, pas déjà évalué) et l'enregistrement forment une seule opération. Si l'enregistrement échoue, rien n'est conservé
  - Clock : Bean Clock fournit l'heure courante à toute l'app. Utile pour le calcul d'urgence
  - Interface : Tailwind CSS par CDN, pour éviter une chaîne de construction Node.js
  - Config : Une seule classe de configuration regroupe la sécurité et Clock

## LOT ALPHA
### Fonctionnalité transverse 1 : Cartographie du réseau
- Besoin = une vue géographique du réseau et de la situation de chaque station
- Périmètre envisagé = un marqueur par station, coloré selon le nombre d'incidents actifs (vert 0, orange 1–2, rouge 3+), avec un lien vers les équipements de la station
- Choix et essais = Leaflet + OpenStreetMap (gratuit, sans clé) ; requête JPQL d'agrégation
- Ce qui fonctionne = carte, couleurs, bulles, navigation et cadrage automatique
- Difficultés = relation à sens unique Equipement -> Station

### Fonctionnalité transverse 2 : Visualisation des donnée
- Besoin = suivre les tendances et la qualité du servic
- Périmètre envisagé = quatre graphiques (incidents par mois, par station, délai moyen de résolution, note moyenne des prestataires)
- Choix et essais = Chart.js par CDN
- Ce qui fonctionne = évolue avec les nouveaux incidents

## BILAN
- Contribution de chacun :
  - Ornella MOTENGO BAUTI
    - Modèle et base de données, 
    - Connexion et sécurité,
    - Stations et équipements, 
    - Cartographie, 
    - Déploiement, 
    - API REST et Swagger, 
    - Gestion des exceptions,
    - Tests
  - Melek USTUN
    - Déclaration et consultation des incidents,
    - Cycle de vie,
    - Affectation des prestataires,
    - Evaluations,
    - Tableau de bord,
    - Restrictions par profil,
    - Visualisation

- Plus grande difficulté et comment elle a été surmontée : 
  - Spring Security, non traité en cours : connexion à notre table, prénom dans le bandeau, CSRF
  - La coordination à deux : fusion de code écrit en parallèle, avec des signatures modifiées par l'une et utilisées par l'autre

- Trois points à retenir du cours et du projet : 
  - @Bean
  - Clock
  - Traiter les exceptions