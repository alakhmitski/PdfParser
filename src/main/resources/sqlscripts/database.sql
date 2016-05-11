SET GLOBAL max_allowed_packet = 1024*1024*50;
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `Vehicle` ;
CREATE SCHEMA IF NOT EXISTS `Vehicle` DEFAULT CHARACTER SET utf8 ;
USE `Vehicle` ;

-- -----------------------------------------------------
-- Table `Vehicle`.`consumer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Vehicle`.`articles` ;

CREATE  TABLE IF NOT EXISTS `Vehicle`.`articles` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL ,
  `content` TEXT,
  `binaryData` LONGBLOB NOT NULL,
  PRIMARY KEY (`id`) )
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;