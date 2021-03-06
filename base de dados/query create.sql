SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;

USE `mydb`;

CREATE  TABLE IF NOT EXISTS `mercado`.`carrinho` (
  `iditem` INT(11) NOT NULL ,
  `idlista` INT(11) NOT NULL ,
  `precoPago` VARCHAR(45) NULL DEFAULT NULL ,
  `isComprou` CHAR(1) NULL DEFAULT NULL ,
  PRIMARY KEY (`iditem`, `idlista`) ,
  INDEX `fk_item_has_lista_lista_idx` (`idlista` ASC) ,
  INDEX `fk_item_has_lista_item_idx` (`iditem` ASC) ,
  CONSTRAINT `fk_item_has_lista_item`
    FOREIGN KEY (`iditem` )
    REFERENCES `mercado`.`item` (`iditem` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_has_lista_lista`
    FOREIGN KEY (`idlista` )
    REFERENCES `mercado`.`lista` (`idlista` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE  TABLE IF NOT EXISTS `mercado`.`item` (
  `iditem` INT(11) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(255) NULL DEFAULT NULL ,
  `preco` DOUBLE(4,2) NULL DEFAULT NULL ,
  `imagem` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`iditem`) )
ENGINE = InnoDB
AUTO_INCREMENT = 35
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE  TABLE IF NOT EXISTS `mercado`.`lista` (
  `idlista` INT(11) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(255) NULL DEFAULT NULL ,
  `cadastro` TIMESTAMP NULL DEFAULT NULL ,
  PRIMARY KEY (`idlista`) )
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
