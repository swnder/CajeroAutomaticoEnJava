/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.5-10.1.21-MariaDB : Database - centralcajeroauto
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`centralcajeroauto` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `centralcajeroauto`;

/*Table structure for table `banco` */

DROP TABLE IF EXISTS `banco`;

CREATE TABLE `banco` (
  `id_banco` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `direccion` int(11) DEFAULT NULL,
  `telefono` int(9) DEFAULT NULL,
  PRIMARY KEY (`id_banco`),
  KEY `direccion1` (`direccion`),
  CONSTRAINT `banco_ibfk_1` FOREIGN KEY (`direccion`) REFERENCES `direccion` (`id_direccion`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `banco` */

insert  into `banco`(`id_banco`,`nombre`,`direccion`,`telefono`) values (1,'Vision Banco S.A',2,975247674),(2,'Banco Familiar S.A.E.C.A',3,331242229),(3,'BBVA',2,331241899),(4,'Banco Nacional de Fomento',4,331242310);

/*Table structure for table `cajeros` */

DROP TABLE IF EXISTS `cajeros`;

CREATE TABLE `cajeros` (
  `id_cajero` int(11) NOT NULL AUTO_INCREMENT,
  `direcion` int(11) DEFAULT NULL,
  `banco` int(11) DEFAULT NULL,
  `saldo` int(50) DEFAULT NULL,
  PRIMARY KEY (`id_cajero`),
  KEY `banco` (`banco`),
  KEY `direcion1` (`direcion`),
  CONSTRAINT `cajeros_ibfk_1` FOREIGN KEY (`banco`) REFERENCES `banco` (`id_banco`),
  CONSTRAINT `cajeros_ibfk_3` FOREIGN KEY (`direcion`) REFERENCES `direccion` (`id_direccion`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `cajeros` */

insert  into `cajeros`(`id_cajero`,`direcion`,`banco`,`saldo`) values (1,2,1,3982700),(2,4,4,-900000);

/*Table structure for table `cliente` */

DROP TABLE IF EXISTS `cliente`;

CREATE TABLE `cliente` (
  `nro_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `cedula` int(11) DEFAULT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `direccion` int(11) DEFAULT NULL,
  `telefono` int(9) DEFAULT NULL,
  PRIMARY KEY (`nro_cliente`),
  KEY `direccion1` (`direccion`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`direccion`) REFERENCES `direccion` (`id_direccion`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `cliente` */

insert  into `cliente`(`nro_cliente`,`cedula`,`nombre`,`direccion`,`telefono`) values (1,4358744,'Sandro Castillo',1,985201942),(2,456465,'Pedro Suarez',3,985321654),(3,3355446,'Maria Delgado',4,612123456),(4,121312,'Juan Perez',2,123321321);

/*Table structure for table `direccion` */

DROP TABLE IF EXISTS `direccion`;

CREATE TABLE `direccion` (
  `id_direccion` int(11) NOT NULL AUTO_INCREMENT,
  `calle` varchar(70) DEFAULT NULL,
  `ciudad` varchar(35) DEFAULT NULL,
  `barrio` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id_direccion`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `direccion` */

insert  into `direccion`(`id_direccion`,`calle`,`ciudad`,`barrio`) values (1,'Tte.Cabrera c/ Don Bosco','Concepcion','Itacurubi'),(2,'Pdte. Franco y, 14 de Mayo, Asunción','Concepcion','Centro'),(3,'Pdte. Franco y Cerro Corá','Concepcion','Centro'),(4,'Pdte. Franco y Carlos Antonio Lopez','Concepcion','Centro'),(5,'Mariscal Jose Felix Estigarribia','Concepcion','Centro');

/*Table structure for table `tarjeta` */

DROP TABLE IF EXISTS `tarjeta`;

CREATE TABLE `tarjeta` (
  `id_tarjeta` int(11) NOT NULL AUTO_INCREMENT,
  `num_tarjeta` int(11) NOT NULL,
  `cliente` int(11) DEFAULT NULL,
  `clave` int(4) NOT NULL,
  `tipo_tarjeta` varchar(30) DEFAULT NULL,
  `linea` int(11) DEFAULT NULL,
  `saldo` int(11) DEFAULT NULL,
  `fecha_expedicion` date NOT NULL,
  `fecha_expiracion` date NOT NULL,
  `idbanco` int(11) DEFAULT NULL,
  `estado` varchar(20) NOT NULL,
  PRIMARY KEY (`id_tarjeta`,`num_tarjeta`),
  KEY `clave` (`clave`),
  KEY `cliente` (`cliente`),
  KEY `idbanco` (`idbanco`),
  CONSTRAINT `tarjeta_ibfk_1` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`nro_cliente`),
  CONSTRAINT `tarjeta_ibfk_2` FOREIGN KEY (`idbanco`) REFERENCES `banco` (`id_banco`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `tarjeta` */

insert  into `tarjeta`(`id_tarjeta`,`num_tarjeta`,`cliente`,`clave`,`tipo_tarjeta`,`linea`,`saldo`,`fecha_expedicion`,`fecha_expiracion`,`idbanco`,`estado`) values (1,123123132,1,123,'Debito',500000,1000000,'2018-10-01','2019-05-17',1,'Activo'),(2,654654658,2,555,'Credito',200000,300000,'2017-12-01','2020-05-24',3,'Activo'),(3,123,2,123,'Credito',5000000,4100000,'2018-11-18','2021-11-14',3,' Activo');

/*Table structure for table `transaccion` */

DROP TABLE IF EXISTS `transaccion`;

CREATE TABLE `transaccion` (
  `id_transaccion` int(11) NOT NULL AUTO_INCREMENT,
  `tarjeta` int(11) NOT NULL,
  `cliente` varchar(50) DEFAULT NULL,
  `tipo_tarjeta` varchar(11) DEFAULT NULL,
  `linea` int(11) DEFAULT NULL,
  `saldo` int(11) DEFAULT NULL,
  `banco` varchar(50) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `monto` int(11) DEFAULT NULL,
  `cajero` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_transaccion`,`tarjeta`),
  KEY `transaccion_ibfk_3` (`tarjeta`),
  KEY `cajero` (`cajero`),
  CONSTRAINT `transaccion_ibfk_1` FOREIGN KEY (`tarjeta`) REFERENCES `tarjeta` (`id_tarjeta`),
  CONSTRAINT `transaccion_ibfk_2` FOREIGN KEY (`cajero`) REFERENCES `cajeros` (`id_cajero`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `transaccion` */

insert  into `transaccion`(`id_transaccion`,`tarjeta`,`cliente`,`tipo_tarjeta`,`linea`,`saldo`,`banco`,`fecha`,`monto`,`cajero`) values (1,1,'juANJO',NULL,NULL,NULL,NULL,'2018-10-07',120000,1),(2,2,'mARUO',NULL,NULL,NULL,NULL,'2018-10-17',250000,2),(3,1,'ANDREZ',NULL,NULL,NULL,NULL,'2018-10-09',100000,1),(4,1,'MARIA',NULL,NULL,NULL,NULL,'2018-10-13',150000,2),(5,3,'APOLINARIA',NULL,NULL,NULL,NULL,'2018-11-19',100000,1),(6,3,'RUPERTA',NULL,NULL,NULL,NULL,'2018-11-19',200000,1),(7,3,'NEGRA',NULL,NULL,NULL,NULL,'2018-11-20',500000,1),(8,3,'Pedro Suarez','Credito',5000000,3200000,'BBVA','2018-11-20',200000,2),(9,3,'Pedro Suarez','Credito',5000000,3100000,'BBVA','2018-11-20',100000,2),(10,3,'Pedro Suarez','Credito',5000000,1600000,'BBVA','2018-11-20',1500000,2),(11,3,'Pedro Suarez','Credito',5000000,100000,'BBVA','2018-11-20',1500000,2),(12,3,'Pedro Suarez','Credito',5000000,0,'BBVA','2018-11-20',100000,2),(13,3,'Pedro Suarez','Credito',5000000,0,'BBVA','2018-11-20',100000,2),(14,3,'Pedro Suarez','Credito',5000000,200000,'BBVA','2018-11-20',100000,2),(15,3,'Pedro Suarez','Credito',5000000,100000,'BBVA','2018-11-20',100000,1),(16,3,'Pedro Suarez','Credito',5000000,95000,'BBVA','2018-11-20',5000,1),(17,3,'Pedro Suarez','Credito',5000000,7000000,'BBVA','2018-11-20',12300,1),(18,3,'Pedro Suarez','Credito',5000000,6900000,'BBVA','2018-11-20',100000,2),(19,3,'Pedro Suarez','Credito',5000000,6400000,'BBVA','2018-11-20',500000,2),(20,3,'Pedro Suarez','Credito',5000000,6000000,'BBVA','2018-11-20',400000,2),(21,3,'Pedro Suarez','Credito',5000000,5600000,'BBVA','2018-11-20',400000,2),(22,3,'Pedro Suarez','Credito',5000000,4100000,'BBVA','2018-11-20',1500000,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
