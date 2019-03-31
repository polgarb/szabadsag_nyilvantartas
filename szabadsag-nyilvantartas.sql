-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1:3306
-- Létrehozás ideje: 2019. Már 31. 18:06
-- Kiszolgáló verziója: 5.7.24
-- PHP verzió: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `szabadsag-nyilvantartas`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `dolgozok`
--

DROP TABLE IF EXISTS `dolgozok`;
CREATE TABLE IF NOT EXISTS `dolgozok` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `nev` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_hungarian_ci NOT NULL,
  `szuletesi_datum` date NOT NULL,
  `gyerekek_szama` int(2) NOT NULL,
  `eves_szabadsag` int(2) DEFAULT NULL,
  `maradek_szabadsag` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `dolgozok`
--

INSERT INTO `dolgozok` (`id`, `nev`, `szuletesi_datum`, `gyerekek_szama`, `eves_szabadsag`, `maradek_szabadsag`) VALUES
(11, 'Albert Gyula', '1980-12-31', 3, 34, 0),
(12, 'Bacsó Gabriella', '1991-09-24', 1, 24, 23),
(13, 'Bán Béla', '1985-07-18', 2, 28, 28),
(14, 'Birkás Lajos', '1959-01-19', 4, 37, 37),
(15, 'Csángo Pál', '1967-07-14', 0, 30, 30),
(16, 'Csontos Károly', '1958-08-12', 6, 37, 37),
(17, 'Dénes László', '1990-05-30', 3, 29, 29),
(18, 'Dobó Ernő', '1986-03-10', 2, 28, 28),
(19, 'Érsek Márta', '1974-07-05', 2, 34, 34),
(20, 'Faragó Anikó', '1999-06-05', 3, 27, 27),
(21, 'Gál Ádám', '1973-09-08', 6, 37, 37),
(22, 'Hegedűs Zoltán', '1962-12-26', 5, 37, 37),
(23, 'Illés Miklós', '1988-07-14', 0, 23, 23),
(24, 'Jakab Ágnes', '1994-09-14', 1, 23, 23),
(25, 'Keleti Lajos', '1989-11-22', 3, 29, 29),
(26, 'Kocsis Judit', '1963-08-03', 1, 32, 32),
(27, 'Kósa Gyula', '1985-08-25', 1, 26, 26),
(28, 'Kovács Rita', '1958-01-19', 0, 30, 30),
(29, 'Kozma Kálmán', '1976-07-15', 2, 33, 33),
(30, 'Lakatos Réka', '1974-10-02', 3, 37, 37),
(31, 'Lázár Tamás', '1981-01-22', 3, 33, 33),
(32, 'Lengyel Mihály', '1955-08-10', 6, 37, 37),
(33, 'Madarász Emil', '1969-12-06', 3, 37, 37),
(34, 'Máté Margit', '1967-01-21', 3, 37, 37),
(35, 'Magyar Zsófia', '1970-12-26', 2, 34, 34),
(36, 'Molnár Dorottya', '1984-02-18', 0, 25, 25),
(37, 'Nagy Zsuzsanna', '1985-11-14', 3, 31, 31),
(38, 'Pál Beáta', '1958-01-27', 3, 37, 37),
(39, 'Pap László', '1988-06-19', 3, 30, 30),
(40, 'Pásztor Mihály', '1970-01-03', 5, 37, 37),
(41, 'Pataky József', '1993-03-19', 3, 28, 28),
(42, 'Poharas Róbert', '1983-11-25', 4, 32, 32),
(43, 'Rácz Mária', '1965-10-26', 2, 34, 34),
(44, 'Sas Ignácz', '1987-01-27', 0, 23, 23),
(45, 'Simon Renáta', '1991-09-23', 0, 22, 22),
(46, 'Sós Miklós', '1995-04-06', 2, 24, 24),
(47, 'Szabó Mária', '1959-08-15', 1, 32, 32),
(48, 'Szűcs György', '1980-07-18', 0, 27, 27),
(49, 'Tatai Márk', '1988-11-15', 4, 30, 30),
(50, 'Török Krisztina', '1995-05-18', 0, 20, 20),
(51, 'Tóth János', '1991-01-26', 1, 24, 24),
(52, 'Varga István', '1988-03-18', 1, 25, 25),
(53, 'Vigh Éva', '1976-08-20', 1, 31, 31);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `extranapok`
--

DROP TABLE IF EXISTS `extranapok`;
CREATE TABLE IF NOT EXISTS `extranapok` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datum` date NOT NULL,
  `ertek` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `extranapok`
--

INSERT INTO `extranapok` (`id`, `datum`, `ertek`) VALUES
(1, '2019-03-15', -1),
(2, '2019-05-01', -1),
(3, '2019-08-10', 1),
(4, '2019-12-07', 1),
(5, '2019-12-14', 1),
(6, '2019-01-01', -1),
(7, '2019-04-19', -1),
(9, '2019-04-22', -1),
(10, '2019-06-10', -1),
(11, '2019-08-20', -1),
(12, '2019-10-23', -1),
(13, '2019-11-01', -1),
(14, '2019-12-25', -1),
(15, '2019-12-26', -1),
(16, '2019-08-19', -1),
(17, '2019-12-24', -1),
(18, '2019-12-27', -1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `szabadsagok`
--

DROP TABLE IF EXISTS `szabadsagok`;
CREATE TABLE IF NOT EXISTS `szabadsagok` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dolgozoid` int(3) NOT NULL,
  `szabadsag_kezdete` date NOT NULL,
  `szabadsag_vege` date NOT NULL,
  `szabadsag_hossza` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dolgozoid` (`dolgozoid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `szabadsagok`
--

INSERT INTO `szabadsagok` (`id`, `dolgozoid`, `szabadsag_kezdete`, `szabadsag_vege`, `szabadsag_hossza`) VALUES
(4, 11, '2019-03-18', '2019-03-29', 10),
(15, 11, '2019-03-15', '2019-03-15', 0),
(16, 11, '2019-04-15', '2019-04-28', 8),
(17, 11, '2019-04-01', '2019-04-14', 10),
(18, 11, '2019-08-05', '2019-08-11', 6),
(19, 12, '2019-03-04', '2019-03-04', 1),
(20, 12, '2019-03-31', '2019-03-31', 0);

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `szabadsagok`
--
ALTER TABLE `szabadsagok`
  ADD CONSTRAINT `szabadsagok_ibfk_1` FOREIGN KEY (`dolgozoid`) REFERENCES `dolgozok` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
