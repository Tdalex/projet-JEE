-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Ven 14 Juillet 2017 à 09:58
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `url_shortener`
--

-- --------------------------------------------------------

--
-- Structure de la table `url_data`
--

CREATE TABLE `url_data` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `long_url` text NOT NULL,
  `start_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_enabled` tinyint(4) NOT NULL DEFAULT '1',
  `end_date` datetime(6) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `number_views` int(10) NOT NULL DEFAULT '0',
  `max_views` int(10) NOT NULL DEFAULT '0',
  `author` int(11) DEFAULT NULL,
  `custom_short` varchar(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `url_data`
--

INSERT INTO `url_data` (`id`, `long_url`, `start_date`, `is_enabled`, `end_date`, `password`, `number_views`, `max_views`, `author`, `custom_short`) VALUES
(13, 'totozeze', '1994-12-12 00:00:00', 1, '2020-12-12 00:00:00.000000', 'aze', 0, 21, 1, NULL),
(15, 'plolp', '1999-11-11 00:00:00', 1, '2057-12-12 00:00:00.000000', 'plop', 0, 987, 1, NULL),
(16, 'coin', '1997-12-12 00:00:00', 1, '2007-12-12 00:00:00.000000', 'azeea', 0, 123, 1, 'aze'),
(17, 'opopo', '1997-12-12 00:00:00', 1, '0201-12-12 00:00:00.000000', 'azeaze', 0, 123, 1, NULL),
(18, 'azezae', '1997-12-04 00:00:00', 1, '2007-12-12 00:00:00.000000', 'azezazeaz', 0, 123, 1, 'iuazezeazeazezaezaez');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`) VALUES
(1, 'toto', 'toto'),
(2, 'tata', 'tata'),
(3, 'tutu', 'tutu'),
(4, 'tptp', 'tptp');

--
-- Index pour les tables exportées
--

--
-- Index pour la table `url_data`
--
ALTER TABLE `url_data`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `url_data`
--
ALTER TABLE `url_data`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
