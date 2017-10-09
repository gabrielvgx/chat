/*
Created: 09/10/2017
Modified: 09/10/2017
Model: MySQL 5.6
Database: MySQL 5.6
*/


-- Create tables section -------------------------------------------------

-- Table usuario

CREATE TABLE `usuario`
(
  `id_usuario` Int(11) NOT NULL AUTO_INCREMENT,
  `nom_usuario` Varchar(30) NOT NULL,
  `senha` Varchar(20),
  `id_sala` Int(11) NOT NULL,
  `admSala` Bool NOT NULL DEFAULT false,
  PRIMARY KEY (`id_usuario`,`id_sala`)
)
;

-- Table mensagem

CREATE TABLE `mensagem`
(
  `id_mensagem` Int(11) NOT NULL AUTO_INCREMENT,
  `txt_mensagem` Varchar(100) NOT NULL,
  `datetime` Datetime NOT NULL,
  `id_usuario` Int(11) NOT NULL,
  `id_sala` Int(11) NOT NULL,
  PRIMARY KEY (`id_mensagem`,`id_usuario`,`id_sala`)
)
;

-- Table sala

CREATE TABLE `sala`
(
  `id_sala` Int(11) NOT NULL AUTO_INCREMENT,
  `nom_sala` Varchar(15) NOT NULL,
  PRIMARY KEY (`id_sala`)
)
;

ALTER TABLE `sala` ADD UNIQUE `nom_sala` (`nom_sala`)
;

-- Create relationships section ------------------------------------------------- 

ALTER TABLE `mensagem` ADD CONSTRAINT `Relationship1` FOREIGN KEY (`id_usuario`, `id_sala`) REFERENCES `usuario` (`id_usuario`, `id_sala`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `usuario` ADD CONSTRAINT `Relationship2` FOREIGN KEY (`id_sala`) REFERENCES `sala` (`id_sala`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `mensagem` ADD CONSTRAINT `Relationship3` FOREIGN KEY (`id_sala`) REFERENCES `sala` (`id_sala`) ON DELETE RESTRICT ON UPDATE RESTRICT
;
