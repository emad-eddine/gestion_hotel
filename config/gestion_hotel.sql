-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 21 nov. 2022 à 20:35
-- Version du serveur :  8.0.21
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestion_hotel`
--

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(260) NOT NULL,
  `admin_username` varchar(50) NOT NULL,
  `admin_password` varchar(25) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`admin_id`, `admin_name`, `admin_username`, `admin_password`) VALUES
(1, 'Imed Eddine', 'admin', 'admin');

-- --------------------------------------------------------

--
-- Structure de la table `chambre`
--

DROP TABLE IF EXISTS `chambre`;
CREATE TABLE IF NOT EXISTS `chambre` (
  `room_id` int NOT NULL AUTO_INCREMENT,
  `room_num` varchar(5) NOT NULL,
  `room_cap` int NOT NULL,
  `room_wifi` varchar(150) NOT NULL,
  `room_price` double NOT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `checkin`
--

DROP TABLE IF EXISTS `checkin`;
CREATE TABLE IF NOT EXISTS `checkin` (
  `id_checkin` int NOT NULL AUTO_INCREMENT,
  `room_reserv_id` int NOT NULL,
  `room_id` int NOT NULL,
  `client_id` int NOT NULL,
  `employe_id` int NOT NULL,
  `date_checkin` date NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id_checkin`),
  KEY `room_reserv_id` (`room_reserv_id`),
  KEY `room_id` (`room_id`),
  KEY `client_id` (`client_id`),
  KEY `emloye_id` (`employe_id`),
  KEY `employe_id` (`employe_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `checkout`
--

DROP TABLE IF EXISTS `checkout`;
CREATE TABLE IF NOT EXISTS `checkout` (
  `id_checkout` int NOT NULL AUTO_INCREMENT,
  `room_reserv_id` int NOT NULL,
  `room_id` int NOT NULL,
  `client_id` int NOT NULL,
  `employe_id` int NOT NULL,
  `date_checkout` date NOT NULL,
  PRIMARY KEY (`id_checkout`),
  KEY `room_reserv_id` (`room_reserv_id`),
  KEY `room_id` (`room_id`),
  KEY `client_id` (`client_id`),
  KEY `emloye_id` (`employe_id`),
  KEY `employe_id` (`employe_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `client_id` int NOT NULL AUTO_INCREMENT,
  `client_name` varchar(250) NOT NULL,
  `client_birth` varchar(15) NOT NULL,
  `client_mail` varchar(150) NOT NULL,
  `client_username` varchar(60) NOT NULL,
  `client_password` varchar(50) NOT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `employe`
--

DROP TABLE IF EXISTS `employe`;
CREATE TABLE IF NOT EXISTS `employe` (
  `employe_id` int NOT NULL AUTO_INCREMENT,
  `employe_name` varchar(250) NOT NULL,
  `employe_birth` varchar(15) NOT NULL,
  `employe_addr` varchar(250) NOT NULL,
  `employe_phone` varchar(20) NOT NULL,
  `employe_username` varchar(50) NOT NULL,
  `employe_password` varchar(70) NOT NULL,
  PRIMARY KEY (`employe_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `room_reserv`
--

DROP TABLE IF EXISTS `room_reserv`;
CREATE TABLE IF NOT EXISTS `room_reserv` (
  `room_reserv_id` int NOT NULL AUTO_INCREMENT,
  `room_id` int NOT NULL,
  `client_id` int NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`room_reserv_id`),
  KEY `room_id` (`room_id`),
  KEY `client_id` (`client_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
