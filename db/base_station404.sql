-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3307
-- Généré le : lun. 26 oct. 2026 à 18:11
-- Version du serveur : 11.4.9-MariaDB
-- Version de PHP : 8.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `base_station404`
--

-- --------------------------------------------------------

--
-- Structure de la table `equipement`
--

DROP TABLE IF EXISTS `equipement`;
CREATE TABLE IF NOT EXISTS `equipement` (
  `id_equipement` int(11) NOT NULL AUTO_INCREMENT,
  `reference` varchar(30) NOT NULL,
  `libelle` varchar(200) NOT NULL,
  `type` enum('ASCENSEUR','ESCALIER_MECANIQUE','PORTILLON','DISTRIBUTEUR','ECRAN_INFORMATION','CAMERA') NOT NULL,
  `id_station` int(11) NOT NULL,
  `etat` enum('EN_SERVICE','EN_PANNE','EN_MAINTENANCE','HORS_SERVICE') NOT NULL DEFAULT 'EN_SERVICE',
  PRIMARY KEY (`id_equipement`),
  UNIQUE KEY `uk_equipement_reference` (`reference`),
  KEY `fk_equipement_station` (`id_station`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `equipement`
--

INSERT INTO `equipement` (`id_equipement`, `reference`, `libelle`, `type`, `id_station`, `etat`) VALUES
(1, 'ESC-CHA-001', 'Escalator mécanique quai 1 - sortie Rivoli', 'ESCALIER_MECANIQUE', 1, 'EN_SERVICE'),
(2, 'ASC-CHA-001', 'Ascenseur accès PMR hall principal', 'ASCENSEUR', 1, 'EN_PANNE'),
(3, 'POR-CHA-001', 'Portillon d\'accès automatique ligne A', 'PORTILLON', 1, 'EN_SERVICE'),
(4, 'DIS-CHA-001', 'Distributeur de titres de transport hall nord', 'DISTRIBUTEUR', 1, 'EN_PANNE'),
(5, 'ESC-LYO-001', 'Escalator montant couloir correspondance', 'ESCALIER_MECANIQUE', 2, 'EN_MAINTENANCE'),
(6, 'ASC-LYO-001', 'Ascenseur quai direction Vincennes', 'ASCENSEUR', 2, 'EN_SERVICE'),
(7, 'ECR-LYO-001', 'Écran d\'information voyageurs quai 2', 'ECRAN_INFORMATION', 2, 'EN_PANNE'),
(8, 'CAM-LYO-001', 'Caméra de vidéosurveillance couloir est', 'CAMERA', 2, 'EN_SERVICE'),
(9, 'POR-NAT-001', 'Portillon d\'accès automatique entrée principale', 'PORTILLON', 3, 'EN_SERVICE'),
(10, 'DIS-NAT-001', 'Distributeur de titres de transport hall est', 'DISTRIBUTEUR', 3, 'HORS_SERVICE'),
(11, 'ESC-NAT-001', 'Escalator descendant sortie place de la Nation', 'ESCALIER_MECANIQUE', 3, 'EN_PANNE'),
(12, 'ASC-REP-001', 'Ascenseur accès quai ligne 5', 'ASCENSEUR', 4, 'EN_SERVICE'),
(13, 'ECR-REP-001', 'Écran d\'information voyageurs hall', 'ECRAN_INFORMATION', 4, 'EN_SERVICE'),
(14, 'CAM-REP-001', 'Caméra de vidéosurveillance quai central', 'CAMERA', 4, 'EN_PANNE'),
(15, 'ESC-DEF-001', 'Escalator mécanique niveau -1 vers RER', 'ESCALIER_MECANIQUE', 5, 'EN_SERVICE'),
(16, 'ESC-DEF-002', 'Escalator mécanique niveau -2 vers métro', 'ESCALIER_MECANIQUE', 5, 'EN_PANNE'),
(17, 'POR-DEF-001', 'Portillon d\'accès automatique zone nord', 'PORTILLON', 5, 'EN_SERVICE'),
(18, 'DIS-DEF-001', 'Distributeur de titres de transport galerie', 'DISTRIBUTEUR', 5, 'EN_PANNE'),
(19, 'ASC-MON-001', 'Ascenseur accès PMR rue du Départ', 'ASCENSEUR', 6, 'EN_PANNE'),
(20, 'ECR-MON-001', 'Écran d\'information voyageurs correspondance', 'ECRAN_INFORMATION', 6, 'EN_SERVICE'),
(21, 'CAM-MON-001', 'Caméra de vidéosurveillance escalier principal', 'CAMERA', 6, 'EN_SERVICE');

-- --------------------------------------------------------

--
-- Structure de la table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE IF NOT EXISTS `evaluation` (
  `id_evaluation` int(11) NOT NULL AUTO_INCREMENT,
  `id_incident` int(11) NOT NULL,
  `note` tinyint(4) NOT NULL,
  `commentaire` varchar(500) DEFAULT NULL,
  `date_evaluation` date NOT NULL,
  PRIMARY KEY (`id_evaluation`),
  UNIQUE KEY `uk_evaluation_incident` (`id_incident`)
) ;

--
-- Déchargement des données de la table `evaluation`
--

INSERT INTO `evaluation` (`id_evaluation`, `id_incident`, `note`, `commentaire`, `date_evaluation`) VALUES
(1, 11, 4, 'Intervention rapide, escalator remis en service le jour même.', '2026-09-18'),
(2, 12, 3, 'Résolu mais il a fallu attendre le lendemain matin.', '2026-09-23'),
(3, 13, 2, 'Délai de résolution trop long pour un équipement PMR.', '2026-09-26'),
(4, 14, 5, 'Caméra réglée proprement, technicien très professionnel.', '2026-09-29'),
(5, 15, 4, NULL, '2026-10-03'),
(6, 16, 3, 'Le problème est revenu une semaine après, à surveiller.', '2026-10-04'),
(7, 17, 5, 'Panne corrigée en moins d\'une journée.', '2026-10-05'),
(8, 18, 4, 'Bon travail, remplacement du moteur effectué.', '2026-10-08'),
(9, 19, 1, 'Prestataire arrivé très tard, intervention insuffisante.', '2026-10-10'),
(10, 21, 5, 'Écran remplacé, tout fonctionne parfaitement.', '2026-10-13');

-- --------------------------------------------------------

--
-- Structure de la table `incident`
--

DROP TABLE IF EXISTS `incident`;
CREATE TABLE IF NOT EXISTS `incident` (
  `id_incident` int(11) NOT NULL AUTO_INCREMENT,
  `id_equipement` int(11) NOT NULL,
  `description` varchar(500) NOT NULL,
  `date_ouverture` datetime NOT NULL,
  `statut` enum('NON_AFFECTE','AFFECTE','RESOLU') NOT NULL DEFAULT 'NON_AFFECTE',
  `id_prestataire` int(11) DEFAULT NULL,
  `date_resolution` datetime DEFAULT NULL,
  PRIMARY KEY (`id_incident`),
  KEY `idx_incident_statut` (`statut`),
  KEY `idx_incident_date_resolution` (`date_resolution`),
  KEY `fk_incident_equipement` (`id_equipement`),
  KEY `fk_incident_prestataire` (`id_prestataire`)
) ;

--
-- Déchargement des données de la table `incident`
--

INSERT INTO `incident` (`id_incident`, `id_equipement`, `description`, `date_ouverture`, `statut`, `id_prestataire`, `date_resolution`) VALUES
(1, 19, 'Ascenseur bloqué au niveau quai, appels non pris en compte.', '2026-10-26 13:21:33', 'NON_AFFECTE', NULL, NULL),
(2, 7, 'Écran noir, plus aucune information voyageurs affichée.', '2026-10-24 16:21:33', 'NON_AFFECTE', NULL, NULL),
(3, 11, 'Escalator à l\'arrêt après un arrêt d\'urgence déclenché.', '2026-10-24 15:21:33', 'NON_AFFECTE', NULL, NULL),
(4, 18, 'Distributeur n\'imprime plus les tickets.', '2026-10-24 03:21:33', 'NON_AFFECTE', NULL, NULL),
(5, 4, 'Écran tactile du distributeur figé sur la page d\'accueil.', '2026-10-23 16:21:33', 'NON_AFFECTE', NULL, NULL),
(6, 14, 'Caméra hors ligne, aucun flux reçu au poste de contrôle.', '2026-10-23 15:21:33', 'NON_AFFECTE', NULL, NULL),
(7, 10, 'Lecteur de carte bancaire hors service, écran tactile figé.', '2026-10-21 15:21:33', 'NON_AFFECTE', NULL, NULL),
(8, 2, 'Ascenseur bloqué entre deux niveaux, porte ne s\'ouvre plus.', '2026-10-23 07:21:33', 'AFFECTE', 1, NULL),
(9, 16, 'Escalator à l\'arrêt, bruit de frottement métallique avant la panne.', '2026-10-25 19:21:33', 'AFFECTE', 2, NULL),
(10, 5, 'Maintenance préventive : remplacement des chaînes d\'entraînement.', '2026-10-25 09:21:33', 'AFFECTE', 2, NULL),
(11, 1, 'Marches qui sautent par intermittence sur la partie haute.', '2026-09-16 15:21:33', 'RESOLU', 2, '2026-09-16 22:21:33'),
(12, 3, 'Portillon qui ne reconnaît pas les titres de transport.', '2026-09-21 15:21:33', 'RESOLU', 3, '2026-09-22 08:21:33'),
(13, 6, 'Ascenseur affichant une erreur E42 et refusant les appels.', '2026-09-24 15:21:33', 'RESOLU', 1, '2026-09-25 12:21:33'),
(14, 8, 'Image floue et décalage de l\'horodatage sur la caméra.', '2026-09-27 15:21:33', 'RESOLU', 5, '2026-09-28 11:21:33'),
(15, 9, 'Portillon bloqué en position fermée en heure de pointe.', '2026-10-01 15:21:33', 'RESOLU', 3, '2026-10-01 17:36:33'),
(16, 12, 'Temps de fermeture des portes anormalement long.', '2026-10-02 15:21:33', 'RESOLU', 1, '2026-10-03 12:21:33'),
(17, 13, 'Affichage des horaires erroné (retard de 15 minutes).', '2026-10-03 15:21:33', 'RESOLU', 4, '2026-10-03 22:21:33'),
(18, 15, 'Escalator ralenti, vitesse irrégulière.', '2026-10-06 15:21:33', 'RESOLU', 2, '2026-10-07 19:21:33'),
(19, 17, 'Barrière du portillon restée ouverte en permanence.', '2026-10-08 15:21:33', 'RESOLU', 3, '2026-10-09 04:21:33'),
(20, 18, 'Bourrage papier, résolu sur place par l\'agent de station.', '2026-10-09 15:21:33', 'RESOLU', NULL, '2026-10-09 18:51:33'),
(21, 20, 'Écran d\'information qui redémarre en boucle.', '2026-10-11 15:21:33', 'RESOLU', 4, '2026-10-11 20:51:33'),
(22, 2, 'Porte palière de l\'ascenseur qui se rouvre sans arrêt.', '2026-10-16 15:21:33', 'RESOLU', 1, '2026-10-17 21:21:33'),
(23, 7, 'Écran affichant des horaires d\'une autre ligne.', '2026-10-21 15:21:33', 'RESOLU', 4, '2026-10-21 19:21:33');

-- --------------------------------------------------------

--
-- Structure de la table `prestataire`
--

DROP TABLE IF EXISTS `prestataire`;
CREATE TABLE IF NOT EXISTS `prestataire` (
  `id_prestataire` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `email` varchar(150) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_prestataire`)
) ;

--
-- Déchargement des données de la table `prestataire`
--

INSERT INTO `prestataire` (`id_prestataire`, `nom`, `email`, `telephone`) VALUES
(1, 'Otis Maintenance France', 'contact@otis-maintenance.example.fr', '01 40 12 34 56'),
(2, 'Schindler Services', 'support@schindler-services.example.fr', '01 45 67 89 01'),
(3, 'Elec Réseaux IDF', 'intervention@elecreseaux.example.fr', '01 48 22 33 44'),
(4, 'Technivision Écrans', 'sav@technivision.example.fr', '01 55 66 77 88'),
(5, 'Sécuritas Vidéosurveillance', 'hotline@securitas-video.example.fr', '01 42 98 76 54');

-- --------------------------------------------------------

--
-- Structure de la table `station`
--

DROP TABLE IF EXISTS `station`;
CREATE TABLE IF NOT EXISTS `station` (
  `id_station` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `latitude` decimal(9,6) NOT NULL,
  `longitude` decimal(9,6) NOT NULL,
  PRIMARY KEY (`id_station`),
  UNIQUE KEY `uk_station_nom` (`nom`)
) ;

--
-- Déchargement des données de la table `station`
--

INSERT INTO `station` (`id_station`, `nom`, `latitude`, `longitude`) VALUES
(1, 'Châtelet', 48.858400, 2.347000),
(2, 'Gare de Lyon', 48.844300, 2.373000),
(3, 'Nation', 48.848300, 2.395900),
(4, 'République', 48.867400, 2.363600),
(5, 'La Défense', 48.891900, 2.238000),
(6, 'Montparnasse', 48.842100, 2.321900);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id_utilisateur` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `mot_de_passe` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `profil` enum('ADMIN','AGENT') NOT NULL,
  PRIMARY KEY (`id_utilisateur`),
  UNIQUE KEY `uk_utilisateur_login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id_utilisateur`, `login`, `mot_de_passe`, `prenom`, `profil`) VALUES
(1, 'admin.sophie', '$2a$10$hRosYfLrTxKDJ2.NKSNaweafEuv4lMU9lX0CjJB9sHCNB18Dja8t.', 'Sophie', 'ADMIN'),
(2, 'admin.karim', '$2a$10$hRosYfLrTxKDJ2.NKSNaweafEuv4lMU9lX0CjJB9sHCNB18Dja8t.', 'Karim', 'ADMIN'),
(3, 'agent.julien', '$2a$10$YYyAxKqzV7eVuIVBvlXMs.ahip4E..DMNlYqbjygDR1HihIG6ceMi', 'Julien', 'AGENT'),
(4, 'agent.nadia', '$2a$10$YYyAxKqzV7eVuIVBvlXMs.ahip4E..DMNlYqbjygDR1HihIG6ceMi', 'Nadia', 'AGENT');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `equipement`
--
ALTER TABLE `equipement`
  ADD CONSTRAINT `fk_equipement_station` FOREIGN KEY (`id_station`) REFERENCES `station` (`id_station`);

--
-- Contraintes pour la table `evaluation`
--
ALTER TABLE `evaluation`
  ADD CONSTRAINT `fk_evaluation_incident` FOREIGN KEY (`id_incident`) REFERENCES `incident` (`id_incident`);

--
-- Contraintes pour la table `incident`
--
ALTER TABLE `incident`
  ADD CONSTRAINT `fk_incident_equipement` FOREIGN KEY (`id_equipement`) REFERENCES `equipement` (`id_equipement`),
  ADD CONSTRAINT `fk_incident_prestataire` FOREIGN KEY (`id_prestataire`) REFERENCES `prestataire` (`id_prestataire`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
