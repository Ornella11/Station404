-- =====================================================================
-- Station404 : création de la base et des données de démonstration
-- Usage : importer db/station404.sql dans phpMyAdmin
-- Le script est rejouable : il supprime puis recrée toutes les tables.
-- =====================================================================

CREATE DATABASE IF NOT EXISTS base_station404
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE base_station404;

-- Pas de SET time_zone : les dates relatives (NOW()) doivent être calculées
-- dans le même fuseau que l'horloge de l'application, sinon les seuils
-- 48 h / 72 h seraient décalés d'une ou deux heures.

-- Toutes les dates de démonstration sont relatives à cet instant unique,
-- pour que les indicateurs d'urgence couvrent les cas limites (§4).
SET @maintenant = NOW();

DROP TABLE IF EXISTS evaluation;
DROP TABLE IF EXISTS incident;
DROP TABLE IF EXISTS utilisateur;
DROP TABLE IF EXISTS equipement;
DROP TABLE IF EXISTS prestataire;
DROP TABLE IF EXISTS station;

-- ---------------------------------------------------------------------
-- Structure
-- ---------------------------------------------------------------------

CREATE TABLE station (
                         id_station INT          NOT NULL AUTO_INCREMENT,
                         nom        VARCHAR(100) NOT NULL,
                         latitude   DOUBLE       NOT NULL,
                         longitude  DOUBLE       NOT NULL,
                         PRIMARY KEY (id_station),
                         UNIQUE KEY uk_station_nom (nom),
                         CONSTRAINT ck_station_latitude  CHECK (latitude  BETWEEN -90  AND 90),
                         CONSTRAINT ck_station_longitude CHECK (longitude BETWEEN -180 AND 180)
) ENGINE=InnoDB;

CREATE TABLE equipement (
                            id_equipement INT          NOT NULL AUTO_INCREMENT,
                            reference     VARCHAR(30)  NOT NULL,
                            libelle       VARCHAR(200) NOT NULL,
                            type          ENUM('ASCENSEUR','ESCALIER_MECANIQUE','PORTILLON',
                                'DISTRIBUTEUR','ECRAN_INFORMATION','CAMERA') NOT NULL,
                            id_station    INT          NOT NULL,
                            etat          ENUM('EN_SERVICE','EN_PANNE','EN_MAINTENANCE','HORS_SERVICE')
                                NOT NULL DEFAULT 'EN_SERVICE',
                            PRIMARY KEY (id_equipement),
                            UNIQUE KEY uk_equipement_reference (reference),            -- RG9
                            CONSTRAINT fk_equipement_station FOREIGN KEY (id_station)
                                REFERENCES station (id_station)
) ENGINE=InnoDB;

CREATE TABLE prestataire (
                             id_prestataire INT          NOT NULL AUTO_INCREMENT,
                             nom            VARCHAR(100) NOT NULL,
                             email          VARCHAR(150) NULL,
                             telephone      VARCHAR(20)  NULL,
                             PRIMARY KEY (id_prestataire),
                             CONSTRAINT ck_prestataire_contact
                                 CHECK (email IS NOT NULL OR telephone IS NOT NULL)
) ENGINE=InnoDB;

CREATE TABLE incident (
                          id_incident     INT          NOT NULL AUTO_INCREMENT,
                          id_equipement   INT          NOT NULL,
                          description     VARCHAR(500) NOT NULL,
                          date_ouverture  DATETIME     NOT NULL,
                          statut          ENUM('NON_AFFECTE','AFFECTE','RESOLU')
                                                       NOT NULL DEFAULT 'NON_AFFECTE',   -- RG1, RG2
                          id_prestataire  INT          NULL,
                          date_resolution DATETIME     NULL,
                          PRIMARY KEY (id_incident),
                          KEY idx_incident_statut (statut),
                          KEY idx_incident_date_resolution (date_resolution),
                          CONSTRAINT fk_incident_equipement FOREIGN KEY (id_equipement)
                              REFERENCES equipement (id_equipement),
                          CONSTRAINT fk_incident_prestataire FOREIGN KEY (id_prestataire)
                              REFERENCES prestataire (id_prestataire),
    -- Le statut doit rester cohérent avec les colonnes dont il dépend (RG2, RG5).
                          CONSTRAINT ck_incident_coherence_statut CHECK (
                              (statut = 'NON_AFFECTE' AND id_prestataire IS NULL     AND date_resolution IS NULL)
                                  OR (statut = 'AFFECTE'     AND id_prestataire IS NOT NULL AND date_resolution IS NULL)
                                  OR (statut = 'RESOLU'      AND date_resolution IS NOT NULL)
                              ),
                          CONSTRAINT ck_incident_resolution_apres_ouverture
                              CHECK (date_resolution IS NULL OR date_resolution >= date_ouverture)  -- RG5
) ENGINE=InnoDB;

CREATE TABLE evaluation (
                            id_evaluation   INT          NOT NULL AUTO_INCREMENT,
                            id_incident     INT          NOT NULL,
                            note            TINYINT      NOT NULL,
                            commentaire     VARCHAR(500) NULL,
                            date_evaluation DATE         NOT NULL,
                            PRIMARY KEY (id_evaluation),
                            UNIQUE KEY uk_evaluation_incident (id_incident),            -- RG8 : une seule par incident
                            CONSTRAINT fk_evaluation_incident FOREIGN KEY (id_incident)
                                REFERENCES incident (id_incident),
                            CONSTRAINT ck_evaluation_note CHECK (note BETWEEN 1 AND 5)  -- RG8
) ENGINE=InnoDB;

CREATE TABLE utilisateur (
                             id_utilisateur INT          NOT NULL AUTO_INCREMENT,
                             login          VARCHAR(50)  NOT NULL,
                             mot_de_passe   VARCHAR(100) NOT NULL,   -- hachage BCrypt, jamais en clair
                             prenom         VARCHAR(100) NOT NULL,
                             profil         ENUM('ADMIN','AGENT') NOT NULL,
                             PRIMARY KEY (id_utilisateur),
                             UNIQUE KEY uk_utilisateur_login (login)
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- Données de démonstration
-- ---------------------------------------------------------------------

INSERT INTO station (id_station, nom, latitude, longitude) VALUES
                                                               (1, 'Châtelet',     48.858400, 2.347000),
                                                               (2, 'Gare de Lyon', 48.844300, 2.373000),
                                                               (3, 'Nation',       48.848300, 2.395900),
                                                               (4, 'République',   48.867400, 2.363600),
                                                               (5, 'La Défense',   48.891900, 2.238000),
                                                               (6, 'Montparnasse', 48.842100, 2.321900);

INSERT INTO equipement (id_equipement, reference, libelle, type, id_station, etat) VALUES
                                                                                       (1,  'ESC-CHA-001', 'Escalator mécanique quai 1 - sortie Rivoli',         'ESCALIER_MECANIQUE', 1, 'EN_SERVICE'),
                                                                                       (2,  'ASC-CHA-001', 'Ascenseur accès PMR hall principal',                 'ASCENSEUR',          1, 'EN_PANNE'),
                                                                                       (3,  'POR-CHA-001', 'Portillon d''accès automatique ligne A',             'PORTILLON',          1, 'EN_SERVICE'),
                                                                                       (4,  'DIS-CHA-001', 'Distributeur de titres de transport hall nord',      'DISTRIBUTEUR',       1, 'EN_PANNE'),
                                                                                       (5,  'ESC-LYO-001', 'Escalator montant couloir correspondance',           'ESCALIER_MECANIQUE', 2, 'EN_MAINTENANCE'),
                                                                                       (6,  'ASC-LYO-001', 'Ascenseur quai direction Vincennes',                 'ASCENSEUR',          2, 'EN_SERVICE'),
                                                                                       (7,  'ECR-LYO-001', 'Écran d''information voyageurs quai 2',              'ECRAN_INFORMATION',  2, 'EN_PANNE'),
                                                                                       (8,  'CAM-LYO-001', 'Caméra de vidéosurveillance couloir est',            'CAMERA',             2, 'EN_SERVICE'),
                                                                                       (9,  'POR-NAT-001', 'Portillon d''accès automatique entrée principale',   'PORTILLON',          3, 'EN_SERVICE'),
                                                                                       (10, 'DIS-NAT-001', 'Distributeur de titres de transport hall est',       'DISTRIBUTEUR',       3, 'HORS_SERVICE'),
                                                                                       (11, 'ESC-NAT-001', 'Escalator descendant sortie place de la Nation',     'ESCALIER_MECANIQUE', 3, 'EN_PANNE'),
                                                                                       (12, 'ASC-REP-001', 'Ascenseur accès quai ligne 5',                       'ASCENSEUR',          4, 'EN_SERVICE'),
                                                                                       (13, 'ECR-REP-001', 'Écran d''information voyageurs hall',                'ECRAN_INFORMATION',  4, 'EN_SERVICE'),
                                                                                       (14, 'CAM-REP-001', 'Caméra de vidéosurveillance quai central',           'CAMERA',             4, 'EN_PANNE'),
                                                                                       (15, 'ESC-DEF-001', 'Escalator mécanique niveau -1 vers RER',             'ESCALIER_MECANIQUE', 5, 'EN_SERVICE'),
                                                                                       (16, 'ESC-DEF-002', 'Escalator mécanique niveau -2 vers métro',           'ESCALIER_MECANIQUE', 5, 'EN_PANNE'),
                                                                                       (17, 'POR-DEF-001', 'Portillon d''accès automatique zone nord',           'PORTILLON',          5, 'EN_SERVICE'),
                                                                                       (18, 'DIS-DEF-001', 'Distributeur de titres de transport galerie',        'DISTRIBUTEUR',       5, 'EN_PANNE'),
                                                                                       (19, 'ASC-MON-001', 'Ascenseur accès PMR rue du Départ',                  'ASCENSEUR',          6, 'EN_PANNE'),
                                                                                       (20, 'ECR-MON-001', 'Écran d''information voyageurs correspondance',      'ECRAN_INFORMATION',  6, 'EN_SERVICE'),
                                                                                       (21, 'CAM-MON-001', 'Caméra de vidéosurveillance escalier principal',     'CAMERA',             6, 'EN_SERVICE');

INSERT INTO prestataire (id_prestataire, nom, email, telephone) VALUES
                                                                    (1, 'Otis Maintenance France',     'contact@otis-maintenance.example.fr',   '01 40 12 34 56'),
                                                                    (2, 'Schindler Services',          'support@schindler-services.example.fr', '01 45 67 89 01'),
                                                                    (3, 'Elec Réseaux IDF',            'intervention@elecreseaux.example.fr',   '01 48 22 33 44'),
                                                                    (4, 'Technivision Écrans',         'sav@technivision.example.fr',           '01 55 66 77 88'),
                                                                    (5, 'Sécuritas Vidéosurveillance', 'hotline@securitas-video.example.fr',    '01 42 98 76 54');

-- Incidents actifs : couvrent les cas limites de RG3 et RG4
INSERT INTO incident (id_incident, id_equipement, description, date_ouverture, statut, id_prestataire, date_resolution) VALUES
                                                                                                                            (1,  19, 'Ascenseur bloqué au niveau quai, appels non pris en compte.',        @maintenant - INTERVAL 2 HOUR,  'NON_AFFECTE', NULL, NULL), -- vert
                                                                                                                            (2,  7,  'Écran noir, plus aucune information voyageurs affichée.',            @maintenant - INTERVAL 47 HOUR, 'NON_AFFECTE', NULL, NULL), -- vert (limite)
                                                                                                                            (3,  11, 'Escalator à l''arrêt après un arrêt d''urgence déclenché.',          @maintenant - INTERVAL 48 HOUR, 'NON_AFFECTE', NULL, NULL), -- orange (48 h pile)
                                                                                                                            (4,  18, 'Distributeur n''imprime plus les tickets.',                          @maintenant - INTERVAL 60 HOUR, 'NON_AFFECTE', NULL, NULL), -- orange
                                                                                                                            (5,  4,  'Écran tactile du distributeur figé sur la page d''accueil.',         @maintenant - INTERVAL 71 HOUR, 'NON_AFFECTE', NULL, NULL), -- orange (limite)
                                                                                                                            (6,  14, 'Caméra hors ligne, aucun flux reçu au poste de contrôle.',           @maintenant - INTERVAL 72 HOUR, 'NON_AFFECTE', NULL, NULL), -- rouge (72 h pile)
                                                                                                                            (7,  10, 'Lecteur de carte bancaire hors service, écran tactile figé.',        @maintenant - INTERVAL 5 DAY,   'NON_AFFECTE', NULL, NULL), -- rouge
                                                                                                                            (8,  2,  'Ascenseur bloqué entre deux niveaux, porte ne s''ouvre plus.',       @maintenant - INTERVAL 80 HOUR, 'AFFECTE',     1,    NULL), -- affecté ancien : jamais rouge
                                                                                                                            (9,  16, 'Escalator à l''arrêt, bruit de frottement métallique avant la panne.', @maintenant - INTERVAL 20 HOUR, 'AFFECTE',   2,    NULL),
                                                                                                                            (10, 5,  'Maintenance préventive : remplacement des chaînes d''entraînement.', @maintenant - INTERVAL 30 HOUR, 'AFFECTE',     2,    NULL);

-- Incidents résolus : historique réparti sur plusieurs semaines (F9, visualisation)
INSERT INTO incident (id_incident, id_equipement, description, date_ouverture, statut, id_prestataire, date_resolution) VALUES
                                                                                                                            (11, 1,  'Marches qui sautent par intermittence sur la partie haute.',        @maintenant - INTERVAL 40 DAY, 'RESOLU', 2,    @maintenant - INTERVAL 40 DAY + INTERVAL 7 HOUR),
                                                                                                                            (12, 3,  'Portillon qui ne reconnaît pas les titres de transport.',           @maintenant - INTERVAL 35 DAY, 'RESOLU', 3,    @maintenant - INTERVAL 35 DAY + INTERVAL 17 HOUR),
                                                                                                                            (13, 6,  'Ascenseur affichant une erreur E42 et refusant les appels.',        @maintenant - INTERVAL 32 DAY, 'RESOLU', 1,    @maintenant - INTERVAL 32 DAY + INTERVAL 21 HOUR),
                                                                                                                            (14, 8,  'Image floue et décalage de l''horodatage sur la caméra.',           @maintenant - INTERVAL 29 DAY, 'RESOLU', 5,    @maintenant - INTERVAL 29 DAY + INTERVAL 20 HOUR),
                                                                                                                            (15, 9,  'Portillon bloqué en position fermée en heure de pointe.',           @maintenant - INTERVAL 25 DAY, 'RESOLU', 3,    @maintenant - INTERVAL 25 DAY + INTERVAL 135 MINUTE),
                                                                                                                            (16, 12, 'Temps de fermeture des portes anormalement long.',                  @maintenant - INTERVAL 24 DAY, 'RESOLU', 1,    @maintenant - INTERVAL 24 DAY + INTERVAL 21 HOUR),
                                                                                                                            (17, 13, 'Affichage des horaires erroné (retard de 15 minutes).',             @maintenant - INTERVAL 23 DAY, 'RESOLU', 4,    @maintenant - INTERVAL 23 DAY + INTERVAL 7 HOUR),
                                                                                                                            (18, 15, 'Escalator ralenti, vitesse irrégulière.',                           @maintenant - INTERVAL 20 DAY, 'RESOLU', 2,    @maintenant - INTERVAL 20 DAY + INTERVAL 28 HOUR),
                                                                                                                            (19, 17, 'Barrière du portillon restée ouverte en permanence.',               @maintenant - INTERVAL 18 DAY, 'RESOLU', 3,    @maintenant - INTERVAL 18 DAY + INTERVAL 13 HOUR),
                                                                                                                            (20, 18, 'Bourrage papier, résolu sur place par l''agent de station.',        @maintenant - INTERVAL 17 DAY, 'RESOLU', NULL, @maintenant - INTERVAL 17 DAY + INTERVAL 210 MINUTE),
                                                                                                                            (21, 20, 'Écran d''information qui redémarre en boucle.',                     @maintenant - INTERVAL 15 DAY, 'RESOLU', 4,    @maintenant - INTERVAL 15 DAY + INTERVAL 330 MINUTE),
                                                                                                                            (22, 2,  'Porte palière de l''ascenseur qui se rouvre sans arrêt.',           @maintenant - INTERVAL 10 DAY, 'RESOLU', 1,    @maintenant - INTERVAL 10 DAY + INTERVAL 30 HOUR),
                                                                                                                            (23, 7,  'Écran affichant des horaires d''une autre ligne.',                  @maintenant - INTERVAL 5 DAY,  'RESOLU', 4,    @maintenant - INTERVAL 5 DAY + INTERVAL 4 HOUR);

-- Les incidents 22 et 23 (résolus, affectés) restent non évalués pour démontrer F10.
-- L'incident 20 (résolu sans prestataire) ne peut pas être évalué (RG8).
INSERT INTO evaluation (id_evaluation, id_incident, note, commentaire, date_evaluation) VALUES
                                                                                            (1,  11, 4, 'Intervention rapide, escalator remis en service le jour même.', DATE(@maintenant - INTERVAL 38 DAY)),
                                                                                            (2,  12, 3, 'Résolu mais il a fallu attendre le lendemain matin.',            DATE(@maintenant - INTERVAL 33 DAY)),
                                                                                            (3,  13, 2, 'Délai de résolution trop long pour un équipement PMR.',          DATE(@maintenant - INTERVAL 30 DAY)),
                                                                                            (4,  14, 5, 'Caméra réglée proprement, technicien très professionnel.',       DATE(@maintenant - INTERVAL 27 DAY)),
                                                                                            (5,  15, 4, NULL,                                                             DATE(@maintenant - INTERVAL 23 DAY)),
                                                                                            (6,  16, 3, 'Le problème est revenu une semaine après, à surveiller.',        DATE(@maintenant - INTERVAL 22 DAY)),
                                                                                            (7,  17, 5, 'Panne corrigée en moins d''une journée.',                        DATE(@maintenant - INTERVAL 21 DAY)),
                                                                                            (8,  18, 4, 'Bon travail, remplacement du moteur effectué.',                  DATE(@maintenant - INTERVAL 18 DAY)),
                                                                                            (9,  19, 1, 'Prestataire arrivé très tard, intervention insuffisante.',       DATE(@maintenant - INTERVAL 16 DAY)),
                                                                                            (10, 21, 5, 'Écran remplacé, tout fonctionne parfaitement.',                  DATE(@maintenant - INTERVAL 13 DAY));

-- Mots de passe des comptes de test : voir la section README du journal.md
INSERT INTO utilisateur (id_utilisateur, login, mot_de_passe, prenom, profil) VALUES
                                                                                  (1, 'admin.sophie', '$2a$10$hRosYfLrTxKDJ2.NKSNaweafEuv4lMU9lX0CjJB9sHCNB18Dja8t.', 'Sophie', 'ADMIN'),
                                                                                  (2, 'admin.karim',  '$2a$10$hRosYfLrTxKDJ2.NKSNaweafEuv4lMU9lX0CjJB9sHCNB18Dja8t.', 'Karim',  'ADMIN'),
                                                                                  (3, 'agent.julien', '$2a$10$YYyAxKqzV7eVuIVBvlXMs.ahip4E..DMNlYqbjygDR1HihIG6ceMi', 'Julien', 'AGENT'),
                                                                                  (4, 'agent.nadia',  '$2a$10$YYyAxKqzV7eVuIVBvlXMs.ahip4E..DMNlYqbjygDR1HihIG6ceMi', 'Nadia',  'AGENT');