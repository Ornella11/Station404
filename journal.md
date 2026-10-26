# Journal — STATION404 (ALTN72, 2026-27)
**Groupe :** Ornella MOTENGO BAUTI, Melek USTUN
## README
- Prérequis : Java …, Maven …, base …
- Initialiser la base : `db/create.sql` (ou procédure …)
- Lancer : `mvn spring-boot:run`
- Application déployée : https://…
- Comptes de test : profil 1 : login / mot de passe ; profil 2 : …
- Limites connues : …
## ARCHITECTURE
- Dépôt : https://…
- Schéma : ![architecture](docs/architecture.png)
## TESTS
- Stratégie : …
- Commande : `mvn verify`
- Couvert : … | Non couvert : … (pourquoi)
## CHOIX TECHNIQUES
- Droits par profil : (tableau du §5.6)
  - `| Fonctionnalité | AGENT | ADMIN |
  |---|---|---|
  | Consulter équipements et fiches | Lecture seul | Accès complet |
  | Déclarer un incident | Accès complet | Accès complet |
  | Consulter / modifier un incident | Accès complet | Accès complet |
  | Affecter ou réaffecter un prestataire | Interdit | Accès complet |
  | Déclarer un incident résolu | Accès complet | Accès complet |
  | Consulter l’historique | Lecture seul | Accès complet |
  | Évaluer un prestataire | Accès complet | Lecture seul |
  | Tableau de bord | Lecture seul | Accès complet |`
- Exceptions : @ControllerAdvice ou ResponseStatusException ? Pourquoi ?
- SOLID : principe(s) appliqué(s) (où, pourquoi) ; principes écartés (pourquoi)
- Requête SQL native : où ? analyse critique
- @Transactional : où ? pourquoi ?
## LOT ALPHA
### Fonctionnalité transverse 1 : …
- Besoin · périmètre envisagé · choix et essais · ce qui fonctionne · difficultés · reste à faire
### Fonctionnalité transverse 2 : …
## BILAN
- Contribution de chacun : …
- Plus grande difficulté et comment elle a été surmontée : …
- Fonctionnalités non réalisées et pourquoi : …
- Trois points à retenir du cours et du projet : …