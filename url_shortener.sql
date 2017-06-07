-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Mer 07 Juin 2017 à 13:19
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
  `long_url` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `url_data`
--

INSERT INTO `url_data` (`id`, `long_url`) VALUES
(1, ''),
(2, 'https://github.com/alexb31/Ruby-project'),
(3, 'rfh'),
(4, 'http://www.routard.com/idees-week-end/cid133610-nos-52-week-ends-coups-de-coeur-dans-les-villes-d-europe.html'),
(5, 'http://www.routard.com/idees-week-end/cid133610-nos-52-week-ends-coups-de-coeur-dans-les-villes-d-europe.html'),
(6, 'http://picbear.com/place/244936460'),
(7, 'https://www.voyagespirates.fr/sejours/vacances-dete-en-sardaigne-7-jours-a-236-euro-dans-une-petite-maison-a-5-minutes-de-la-plage-vols-et-location-de-voiture-inclus_16360'),
(8, 'http://localhost/url_shortener/index.jsp'),
(9, 'https://www.google.fr/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#safe=off&q=zend+framework+tutorial'),
(10, 'https://www.google.fr/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#safe=off&q=zend+framework+tutorial'),
(11, 'http://www.cdiscount.com/'),
(12, 'https://github.com/eelbahri/pokedexRails/tree/master/app');

--
-- Index pour les tables exportées
--

--
-- Index pour la table `url_data`
--
ALTER TABLE `url_data`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `url_data`
--
ALTER TABLE `url_data`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
