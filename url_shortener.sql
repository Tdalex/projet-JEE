-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Mer 07 Juin 2017 à 14:03
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
  `max_views` int(10) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `url_data`
--

INSERT INTO `url_data` (`id`, `long_url`, `start_date`, `is_enabled`, `end_date`, `password`, `number_views`, `max_views`) VALUES
(1, '', '2017-06-07 16:02:49', 1, NULL, NULL, 0, 0),
(2, 'https://github.com/alexb31/Ruby-project', '2017-06-07 16:02:49', 1, NULL, NULL, 0, 0),
(3, 'rfh', '2017-06-07 16:02:49', 1, NULL, NULL, 0, 0),
(4, 'http://www.routard.com/idees-week-end/cid133610-nos-52-week-ends-coups-de-coeur-dans-les-villes-d-europe.html', '2017-06-07 16:02:49', 1, NULL, NULL, 0, 0),
(5, 'http://www.routard.com/idees-week-end/cid133610-nos-52-week-ends-coups-de-coeur-dans-les-villes-d-europe.html', '2017-06-07 16:02:49', 1, NULL, NULL, 0, 0),
(6, 'http://picbear.com/place/244936460', '2017-06-07 16:02:49', 1, NULL, NULL, 0, 0),
(7, 'https://www.voyagespirates.fr/sejours/vacances-dete-en-sardaigne-7-jours-a-236-euro-dans-une-petite-maison-a-5-minutes-de-la-plage-vols-et-location-de-voiture-inclus_16360', '2017-06-07 16:02:49', 1, NULL, NULL, 0, 0),
(8, 'http://localhost/url_shortener/index.jsp', '2017-06-07 16:02:49', 1, NULL, NULL, 0, 0),
(9, 'https://www.google.fr/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#safe=off&q=zend+framework+tutorial', '2017-06-07 16:02:49', 1, NULL, NULL, 0, 0),
(10, 'https://www.google.fr/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#safe=off&q=zend+framework+tutorial', '2017-06-07 16:02:49', 1, NULL, NULL, 0, 0),
(11, 'http://www.cdiscount.com/', '2017-06-07 16:02:49', 1, NULL, NULL, 0, 0),
(12, 'https://github.com/eelbahri/pokedexRails/tree/master/app', '2017-06-07 16:02:49', 1, NULL, NULL, 0, 0);

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
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
