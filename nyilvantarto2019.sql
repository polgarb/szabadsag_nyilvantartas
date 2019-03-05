-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1:3306
-- Létrehozás ideje: 2019. Már 05. 14:30
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
-- Adatbázis: `nyilvantarto2019`
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
  `fizetes_nelkuli` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `dolgozok`
--

INSERT INTO `dolgozok` (`id`, `nev`, `szuletesi_datum`, `gyerekek_szama`, `eves_szabadsag`, `maradek_szabadsag`, `fizetes_nelkuli`) VALUES
(11, 'Albert Gyula', '1980-12-31', 3, 34, 34, 0),
(12, 'Bacsó Gabriella', '1991-09-24', 1, 24, 24, 0),
(13, 'Bán Béla', '1985-07-18', 2, 28, 28, 0),
(14, 'Birkás Lajos', '1959-01-19', 4, 37, 37, 0),
(15, 'Csángo Pál', '1967-07-14', 0, 30, 30, 0),
(16, 'Csontos Károly', '1958-08-12', 6, 37, 37, 0),
(17, 'Dénes László', '1990-05-30', 3, 29, 29, 0),
(18, 'Dobó Ernő', '1986-03-10', 2, 28, 28, 0),
(19, 'Érsek Márta', '1974-07-05', 2, 34, 34, 0),
(20, 'Faragó Anikó', '1999-06-05', 3, 27, 27, 0),
(21, 'Gál Ádám', '1973-09-08', 6, 37, 37, 0),
(22, 'Hegedűs Zoltán', '1962-12-26', 5, 37, 37, 0),
(23, 'Illés Miklós', '1988-07-14', 0, 23, 23, 0),
(24, 'Jakab Ágnes', '1994-09-14', 1, 23, 23, 0),
(25, 'Keleti Lajos', '1989-11-22', 3, 29, 29, 0),
(26, 'Kocsis Judit', '1963-08-03', 1, 32, 32, 0),
(27, 'Kósa Gyula', '1985-08-25', 1, 26, 26, 0),
(28, 'Kovács Rita', '1958-01-19', 0, 30, 30, 0),
(29, 'Kozma Kálmán', '1976-07-15', 2, 33, 33, 0),
(30, 'Lakatos Réka', '1974-10-02', 3, 37, 37, 0),
(31, 'Lázár Tamás', '1981-01-22', 3, 33, 33, 0),
(32, 'Lengyel Mihály', '1955-08-10', 6, 37, 37, 0),
(33, 'Madarász Emil', '1969-12-06', 3, 37, 37, 0),
(34, 'Máté Margit', '1967-01-21', 3, 37, 37, 0),
(35, 'Magyar Zsófia', '1970-12-26', 2, 34, 34, 0),
(36, 'Molnár Dorottya', '1984-02-18', 0, 25, 25, 0),
(37, 'Nagy Zsuzsanna', '1985-11-14', 3, 31, 31, 0),
(38, 'Pál Beáta', '1958-01-27', 3, 37, 37, 0),
(39, 'Pap László', '1988-06-19', 3, 30, 30, 0),
(40, 'Pásztor Mihály', '1970-01-03', 5, 37, 37, 0),
(41, 'Pataky József', '1993-03-19', 3, 28, 28, 0),
(42, 'Poharas Róbert', '1983-11-25', 4, 32, 32, 0),
(43, 'Rácz Mária', '1965-10-26', 2, 34, 34, 0),
(44, 'Sas Ignácz', '1987-01-27', 0, 23, 23, 0),
(45, 'Simon Renáta', '1991-09-23', 0, 22, 22, 0),
(46, 'Sós Miklós', '1995-04-06', 2, 24, 24, 0),
(47, 'Szabó Mária', '1959-08-15', 1, 32, 32, 0),
(48, 'Szűcs György', '1980-07-18', 0, 27, 27, 0),
(49, 'Tatai Márk', '1988-11-15', 4, 30, 30, 0),
(50, 'Török Krisztina', '1995-05-18', 0, 20, 20, 0),
(51, 'Tóth János', '1991-01-26', 1, 24, 24, 0),
(52, 'Varga István', '1988-03-18', 1, 25, 25, 0),
(53, 'Vigh Éva', '1976-08-20', 1, 31, 31, 0);

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
(8, '2019-04-21', -1),
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
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4;

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
